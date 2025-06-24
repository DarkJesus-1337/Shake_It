package com.darkjesus.shakeit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.darkjesus.shakeit.ui.composables.CocktailDetailBottomSheet
import com.darkjesus.shakeit.ui.composables.CocktailList
import com.darkjesus.shakeit.ui.viewmodel.CocktailViewModel
import org.koin.androidx.compose.koinViewModel

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
                        .clickable { searchMode = 0 }
                        .padding(vertical = 8.dp),
                    color = if (searchMode == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Incredient",
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            if (searchMode == 1) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent,
                            RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                        )
                        .clickable {
                            searchMode = 1
                            selectedFilterLetter = ""
                        }
                        .padding(vertical = 8.dp),
                    color = if (searchMode == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }

            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = {
                    viewModel.updateSearchQuery(it)
                    if (it.length > 2) {
                        if (searchMode == 0) {
                            viewModel.searchCocktails(it)
                        } else {
                            viewModel.searchCocktailsByIngredient(it)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                placeholder = {
                    Text(
                        if (searchMode == 0) "Search for cocktails" else "Search for ingredient"
                    )
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
                            "No cocktails found with this ingredient.\nTry another ingredient like 'Vodka', 'Rum', or 'Gin'."
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
                            }
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
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
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
            } else if (uiState.searchQuery.isNotEmpty() && uiState.searchQuery.length > 2) {
                viewModel.searchCocktailsByIngredient(uiState.searchQuery)
            }
        }
    }
}