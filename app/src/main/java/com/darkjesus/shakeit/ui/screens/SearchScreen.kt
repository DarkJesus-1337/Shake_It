package com.darkjesus.shakeit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.darkjesus.shakeit.R
import com.darkjesus.shakeit.ui.composables.CocktailDetailBottomSheet
import com.darkjesus.shakeit.ui.composables.CocktailList
import com.darkjesus.shakeit.ui.viewmodel.CocktailViewModel
import com.darkjesus.shakeit.ui.viewmodel.ViewMode
import org.koin.androidx.compose.koinViewModel

/**
 * A screen for searching cocktails by name or ingredient.
 *
 * This screen provides the following functionality:
 * - Search cocktails by name with a text field
 * - Filter cocktails by first letter using filter chips
 * - Search cocktails by ingredient using a dropdown picker
 * - Toggle between list and grid view modes
 * - View detailed cocktail information in a bottom sheet
 *
 * @param viewModel The ViewModel that manages the UI state and handles user actions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: CocktailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedFilterLetter by remember { mutableStateOf("") }
    val alphabet = ('A'..'Z').map { it.toString() }
    var searchMode by remember { mutableIntStateOf(0) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var isDropdownExpanded by remember { mutableStateOf(false) }
    var selectedIngredient by remember { mutableStateOf("") }
    var ingredientSearchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val filteredIngredients = remember(ingredientSearchQuery, uiState.ingredients) {
        if (ingredientSearchQuery.isEmpty()) {
            uiState.ingredients
        } else {
            uiState.ingredients.filter {
                it.contains(ingredientSearchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_launcher),
                    contentScale = ContentScale.Crop,
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                )

                Spacer(
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = "Search Cocktails",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Name",
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (searchMode == 0) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent,
                            RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                        )
                        .clickable {
                            searchMode = 0
                            selectedFilterLetter = ""
                            viewModel.updateSearchQuery("")
                        }
                        .padding(vertical = 8.dp),
                    color = if (searchMode == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Ingredient",
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (searchMode == 1) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent,
                            RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                        )
                        .clickable {
                            searchMode = 1
                            selectedFilterLetter = ""
                            viewModel.updateSearchQuery("")
                            if (uiState.ingredients.isEmpty()) {
                                viewModel.loadAllIngredients()
                            }
                        }
                        .padding(vertical = 8.dp),
                    color = if (searchMode == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (searchMode == 0) {
                    OutlinedTextField(
                        value = uiState.searchQuery,
                        onValueChange = {
                            viewModel.updateSearchQuery(it)
                            selectedFilterLetter = ""

                            if (it.length > 2) {
                                viewModel.searchCocktails(it)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp)
                            .clickable(onClick = {
                                selectedFilterLetter = ""
                            }, enabled = selectedFilterLetter.isNotEmpty()),
                        placeholder = {
                            Text("Search for cocktails")
                        },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        trailingIcon = {
                            if (uiState.searchQuery.isNotEmpty()) {
                                IconButton(onClick = {
                                    viewModel.updateSearchQuery("")
                                    selectedFilterLetter = ""
                                }) {
                                    Icon(Icons.Default.Clear, contentDescription = "Clear")
                                }
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true
                    )
                } else {
                    Box(modifier = Modifier.weight(1f)) {
                        OutlinedTextField(
                            value = selectedIngredient,
                            onValueChange = { },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            placeholder = {
                                Text("Select an ingredient")
                            },
                            readOnly = true,
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search"
                                )
                            },
                            trailingIcon = {
                                IconButton(onClick = {
                                    isDropdownExpanded = !isDropdownExpanded
                                    if (isDropdownExpanded) {
                                        ingredientSearchQuery = ""
                                    }
                                }) {
                                    Icon(
                                        if (isDropdownExpanded) Icons.Default.KeyboardArrowUp
                                        else Icons.Default.KeyboardArrowDown,
                                        contentDescription = "Toggle dropdown"
                                    )
                                }
                            },
                            shape = RoundedCornerShape(12.dp),
                            singleLine = true
                        )

                        DropdownMenu(
                            expanded = isDropdownExpanded,
                            onDismissRequest = { isDropdownExpanded = false },
                            modifier = Modifier
                                .fillMaxWidth(0.9f)
                                .height(300.dp)
                        ) {
                            OutlinedTextField(
                                value = ingredientSearchQuery,
                                onValueChange = { ingredientSearchQuery = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                placeholder = { Text("Filter ingredients") },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                                trailingIcon = {
                                    if (ingredientSearchQuery.isNotEmpty()) {
                                        IconButton(onClick = { ingredientSearchQuery = "" }) {
                                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(8.dp),
                                singleLine = true
                            )

                            if (uiState.isLoadingIngredients) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .verticalScroll(scrollState)
                                ) {
                                    Column {
                                        filteredIngredients.forEach { ingredient ->
                                            DropdownMenuItem(
                                                text = { Text(ingredient) },
                                                onClick = {
                                                    selectedIngredient = ingredient
                                                    viewModel.updateSearchQuery(ingredient)
                                                    viewModel.searchCocktailsByIngredient(ingredient)
                                                    isDropdownExpanded = false
                                                }
                                            )
                                        }

                                        if (filteredIngredients.isEmpty() && ingredientSearchQuery.isNotEmpty()) {
                                            Text(
                                                text = "No matching ingredients found",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(16.dp),
                                                textAlign = TextAlign.Center,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if (uiState.cocktails.isNotEmpty()) {
                    IconButton(onClick = { viewModel.toggleViewMode() }) {
                        Icon(
                            painter = painterResource(
                                if (uiState.viewMode == ViewMode.LIST)
                                    R.drawable.grid_view else R.drawable.view_list
                            ),
                            contentDescription = "Toggle view mode"
                        )
                    }
                }
            }

            if (searchMode == 0) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(alphabet) { letter ->
                        FilterChip(
                            selected = selectedFilterLetter == letter,
                            onClick = {
                                if (selectedFilterLetter == letter) {
                                    selectedFilterLetter = ""
                                } else {
                                    viewModel.updateSearchQuery("")
                                    selectedFilterLetter = letter
                                    viewModel.searchByFirstLetter(letter)
                                }
                            },
                            label = { Text(letter) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    uiState.cocktails.isEmpty() -> {
                        val message = if (searchMode == 0) {
                            "No cocktails found.\nPlease search for a different term or select a letter."
                        } else {
                            "No cocktails found with this ingredient.\nPlease select an ingredient from the dropdown."
                        }
                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(32.dp)
                        )
                    }

                    else -> {
                        CocktailList(
                            cocktails = uiState.cocktails,
                            onCocktailClick = { cocktail ->
                                viewModel.selectCocktail(cocktail)
                                showBottomSheet = true
                            },
                            viewModel = viewModel,
                            viewMode = uiState.viewMode
                        )
                    }
                }
            }
        }

        if (showBottomSheet && uiState.selectedCocktail != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    viewModel.clearSelection()
                },
                sheetState = sheetState,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                modifier = Modifier.fillMaxHeight(1f)
            ) {
                CocktailDetailBottomSheet(
                    cocktail = uiState.selectedCocktail!!,
                    viewModel = viewModel
                )
            }
        }
    }

    LaunchedEffect(key1 = searchMode) {
        if (uiState.cocktails.isEmpty() && !uiState.isLoading) {
            if (searchMode == 0) {
                viewModel.searchByFirstLetter("A")
                selectedFilterLetter = "A"
            } else {
                if (uiState.ingredients.isEmpty()) {
                    viewModel.loadAllIngredients()
                }

                if (selectedIngredient.isNotEmpty()) {
                    viewModel.searchCocktailsByIngredient(selectedIngredient)
                }
            }
        }

        if (uiState.viewMode == ViewMode.LIST) {
            viewModel.toggleViewMode()
        }
    }
}