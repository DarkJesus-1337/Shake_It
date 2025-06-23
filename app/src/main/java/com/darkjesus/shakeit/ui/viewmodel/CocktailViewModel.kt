package com.darkjesus.shakeit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkjesus.shakeit.data.model.Cocktail
import com.darkjesus.shakeit.data.repository.CocktailRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class CocktailUiState(
    val cocktails: List<Cocktail> = emptyList(),
    val selectedCocktail: Cocktail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = ""
)

class CocktailViewModel(
    private val repository: CocktailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CocktailUiState())
    val uiState: StateFlow<CocktailUiState> = _uiState.asStateFlow()

    init {
        getRandomCocktail()
    }

    fun searchCocktails(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            repository.searchCocktailsByName(query).collect { cocktails ->
                _uiState.value = _uiState.value.copy(
                    cocktails = cocktails,
                    isLoading = false
                )
            }
        }
    }

    fun searchByFirstLetter(letter: String) {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            repository.searchCocktailsByFirstLetter(letter).collect { cocktails ->
                _uiState.value = _uiState.value.copy(
                    cocktails = cocktails,
                    isLoading = false
                )
            }
        }
    }

    fun getRandomCocktail() {
        _uiState.value = _uiState.value.copy(
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            repository.getRandomCocktail().collect { cocktail ->
                _uiState.value = _uiState.value.copy(
                    selectedCocktail = cocktail,
                    cocktails = cocktail?.let { listOf(it) } ?: emptyList(),
                    isLoading = false
                )
            }
        }
    }

    fun selectCocktail(cocktail: Cocktail) {
        _uiState.value = _uiState.value.copy(selectedCocktail = cocktail)
    }

    fun clearSelection() {
        _uiState.value = _uiState.value.copy(selectedCocktail = null)
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }
}