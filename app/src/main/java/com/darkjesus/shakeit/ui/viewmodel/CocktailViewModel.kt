package com.darkjesus.shakeit.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkjesus.shakeit.data.model.Cocktail
import com.darkjesus.shakeit.data.repository.CocktailRepository
import com.darkjesus.shakeit.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class CocktailUiState(
    val cocktails: List<Cocktail> = emptyList(),
    val selectedCocktail: Cocktail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val isLoadingDetails: Boolean = false,
    val favorites: List<Cocktail> = emptyList(),
    val isLoadingFavorites: Boolean = false
)

class CocktailViewModel(
    private val cocktailRepository: CocktailRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CocktailUiState())
    val uiState: StateFlow<CocktailUiState> = _uiState.asStateFlow()

    init {
        getRandomCocktail()
        loadFavorites()
    }

    fun searchCocktails(query: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            cocktailRepository.searchCocktailsByName(query).collect { cocktails ->
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
            cocktailRepository.searchCocktailsByFirstLetter(letter).collect { cocktails ->
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
            cocktailRepository.getRandomCocktail().collect { cocktail ->
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

        if (cocktail.instructions.isNullOrEmpty() || cocktail.ingredients.isEmpty()) {
            loadCocktailDetails(cocktail.id)
        }
    }

    private fun loadCocktailDetails(cocktailId: String) {
        _uiState.value = _uiState.value.copy(isLoadingDetails = true)

        viewModelScope.launch {
            cocktailRepository.getCocktailById(cocktailId).collect { detailedCocktail ->
                if (detailedCocktail != null) {
                    _uiState.value = _uiState.value.copy(
                        selectedCocktail = detailedCocktail,
                        isLoadingDetails = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(isLoadingDetails = false)
                }
            }
        }
    }

    fun clearSelection() {
        _uiState.value = _uiState.value.copy(selectedCocktail = null)
    }

    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun searchCocktailsByIngredient(ingredient: String) {
        _uiState.value = _uiState.value.copy(
            searchQuery = ingredient,
            isLoading = true,
            error = null
        )

        viewModelScope.launch {
            cocktailRepository.searchCocktailsByIngredient(ingredient).collect { cocktails ->
                _uiState.value = _uiState.value.copy(
                    cocktails = cocktails,
                    isLoading = false
                )
            }
        }
    }

    fun loadFavorites() {
        _uiState.value = _uiState.value.copy(isLoadingFavorites = true)

        viewModelScope.launch {
            favoritesRepository.getAllFavorites().collect { favorites ->
                _uiState.value = _uiState.value.copy(
                    favorites = favorites,
                    isLoadingFavorites = false
                )
            }
        }
    }

    fun toggleFavorite(cocktail: Cocktail) {
        viewModelScope.launch {
            try {
                val isFavorite = favoritesRepository.isFavorite(cocktail.id).first()

                if (isFavorite) {
                    favoritesRepository.removeFromFavorites(cocktail.id)
                } else {
                    favoritesRepository.addToFavorites(cocktail)
                }

                loadFavorites()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update favorites: ${e.message}"
                )
            }
        }
    }

    fun isFavorite(cocktailId: String): Flow<Boolean> {
        return favoritesRepository.isFavorite(cocktailId)
    }

    fun clearAllFavorites() {
        viewModelScope.launch {
            try {
                favoritesRepository.clearAllFavorites()
                loadFavorites()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to clear favorites: ${e.message}"
                )
            }
        }
    }
}