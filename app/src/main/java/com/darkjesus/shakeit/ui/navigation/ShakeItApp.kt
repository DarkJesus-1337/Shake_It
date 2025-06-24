package com.darkjesus.shakeit.ui.navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.darkjesus.shakeit.data.model.Cocktail
import com.darkjesus.shakeit.ui.composables.BottomNavigationBar
import com.darkjesus.shakeit.ui.composables.CocktailDetailBottomSheet
import com.darkjesus.shakeit.ui.screens.CocktailOfTheDay
import com.darkjesus.shakeit.ui.screens.FavoritesScreen
import com.darkjesus.shakeit.ui.screens.SearchScreen
import com.darkjesus.shakeit.ui.viewmodel.CocktailViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShakeItApp() {
    val navController = rememberNavController()
    val viewModel: CocktailViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedCocktail by remember { mutableStateOf<Cocktail?>(null) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = CocktailOfTheDayRoute,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<CocktailOfTheDayRoute> {
                CocktailOfTheDay(
                    uiState = uiState,
                    onCocktailClick = { cocktail ->
                        selectedCocktail = cocktail
                        showBottomSheet = true
                    },
                    onRefresh = { viewModel.getRandomCocktail() }
                )
            }
            composable<SearchRoute> {
                SearchScreen()
            }
            composable<FavoritesRoute> {
                FavoritesScreen()
            }
        }

        if (showBottomSheet && selectedCocktail != null) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    selectedCocktail = null
                },
                dragHandle = { BottomSheetDefaults.DragHandle() },
                modifier = Modifier.fillMaxHeight(1f)
            ) {
                CocktailDetailBottomSheet(
                    cocktail = selectedCocktail!!,
                    viewModel = viewModel
                )
            }
        }
    }
}
