package com.darkjesus.shakeit.di

import com.darkjesus.shakeit.data.api.CocktailAPI
import com.darkjesus.shakeit.data.database.FavoritesDatabase
import com.darkjesus.shakeit.data.repository.CocktailRepository
import com.darkjesus.shakeit.data.repository.FavoritesRepository
import com.darkjesus.shakeit.ui.viewmodel.CocktailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { CocktailAPI.retrofitService }

    single { FavoritesDatabase.getDatabase(androidContext()) }
    single { get<FavoritesDatabase>().favoritesDao() }

    singleOf(::CocktailRepository)
    singleOf(::FavoritesRepository)

    viewModelOf(::CocktailViewModel)
}