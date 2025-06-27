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

/**
 * Enum representing the different view modes.
 */
enum class ViewMode {
    LIST,
    GRID
}

/**
 * Data class representing the UI state for cocktail-related screens.
 *
 * @property cocktails List of cocktails currently displayed.
 * @property selectedCocktail Currently selected cocktail for detailed view.
 * @property isLoading Indicates whether cocktails are currently being loaded.
 * @property error Error message to be displayed, if any.
 * @property searchQuery Current search query string.
 * @property isLoadingDetails Indicates whether detailed cocktail information is being loaded.
 * @property favorites List of favorite cocktails.
 * @property isLoadingFavorites Indicates whether favorites are currently being loaded.
 * @property viewMode The current view mode (list or grid).
 * @property ingredients List of all available ingredients for ingredient picker.
 * @property isLoadingIngredients Indicates whether ingredients are currently being loaded.
 */
data class CocktailUiState(
    val cocktails: List<Cocktail> = emptyList(),
    val selectedCocktail: Cocktail? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    var searchQuery: String = "",
    val isLoadingDetails: Boolean = false,
    val favorites: List<Cocktail> = emptyList(),
    val isLoadingFavorites: Boolean = false,
    val viewMode: ViewMode = ViewMode.LIST,
    val ingredients: List<String> = emptyList(),
    val isLoadingIngredients: Boolean = false
)

/**
 * ViewModel that manages cocktail data and user interactions.
 *
 * Handles cocktail search, selection, favorites management, and maintains the UI state.
 *
 * @property cocktailRepository Repository for fetching cocktail data from the API.
 * @property favoritesRepository Repository for managing favorite cocktails in local storage.
 */
class CocktailViewModel(
    private val cocktailRepository: CocktailRepository,
    private val favoritesRepository: FavoritesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CocktailUiState())

    /**
     * Observable UI state for the cocktail screens.
     */
    val uiState: StateFlow<CocktailUiState> = _uiState.asStateFlow()

    init {
        getRandomCocktail()
        loadFavorites()
        loadAllIngredients()
    }

    /**
     * Searches for cocktails by name.
     *
     * @param query The name or partial name to search for.
     */
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

    /**
     * Searches for cocktails by their first letter.
     *
     * @param letter The letter to search for.
     */
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

    /**
     * Fetches a random cocktail from the API.
     */
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

    /**
     * Sets the currently selected cocktail and loads its details if needed.
     *
     * @param cocktail The cocktail to select.
     */
    fun selectCocktail(cocktail: Cocktail) {
        _uiState.value = _uiState.value.copy(selectedCocktail = cocktail)

        if (cocktail.instructions.isNullOrEmpty() || cocktail.ingredients.isEmpty()) {
            loadCocktailDetails(cocktail.id)
        }
    }

    /**
     * Loads detailed information for a specific cocktail.
     *
     * @param cocktailId The ID of the cocktail to load details for.
     */
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

    /**
     * Clears the currently selected cocktail.
     */
    fun clearSelection() {
        _uiState.value = _uiState.value.copy(selectedCocktail = null)
    }

    /**
     * Updates the current search query without triggering a search.
     *
     * @param query The new search query.
     */
    fun updateSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    /**
     * Searches for cocktails that contain a specific ingredient.
     *
     * @param ingredient The ingredient to search for.
     */
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

    /**
     * Loads all favorite cocktails from local storage.
     */
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

    /**
     * Toggles the favorite status of a cocktail.
     * If the cocktail is already a favorite, it will be removed.
     * Otherwise, it will be added to favorites.
     *
     * @param cocktail The cocktail to toggle.
     */
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

    /**
     * Checks if a cocktail is marked as favorite.
     *
     * @param cocktailId The ID of the cocktail to check.
     * @return A Flow emitting true if the cocktail is a favorite, false otherwise.
     */
    fun isFavorite(cocktailId: String): Flow<Boolean> {
        return favoritesRepository.isFavorite(cocktailId)
    }

    /**
     * Removes all cocktails from favorites.
     */
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

    /**
     * Toggles between list and grid view modes.
     */
    fun toggleViewMode() {
        val newMode = if (_uiState.value.viewMode == ViewMode.LIST) ViewMode.GRID else ViewMode.LIST
        _uiState.value = _uiState.value.copy(viewMode = newMode)
    }

    /**
     * Loads all available ingredients from the API.
     *
     * Fetches the complete list of ingredients from the API and updates the UI state.
     * This list is used to populate the ingredient picker in the search screen.
     */
    fun loadAllIngredients() {
        _uiState.value = _uiState.value.copy(isLoadingIngredients = true)

        viewModelScope.launch {
            cocktailRepository.getAllIngredients().collect { ingredients ->
                _uiState.value = _uiState.value.copy(
                    ingredients = ingredients,
                    isLoadingIngredients = false
                )
            }
        }
    }
}