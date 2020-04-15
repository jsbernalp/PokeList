package com.jonathanbernal.pokelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class PokemonViewModelFactory @Inject constructor(
    private val pokemonsViewModel: PokeViewModel): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(pokemonsViewModel::class.java)){
            return pokemonsViewModel as T
        }
        throw IllegalArgumentException("uknown class name")
    }

}