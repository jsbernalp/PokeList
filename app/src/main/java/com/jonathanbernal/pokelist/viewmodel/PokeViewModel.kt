package com.jonathanbernal.pokelist.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathanbernal.pokelist.model.PokemonResponse
import com.jonathanbernal.pokelist.repository.PokeRepository
import javax.inject.Inject

class PokeViewModel @Inject constructor(
    private val repository: PokeRepository):ViewModel(){

    init {
        getPokemons()
    }

    var pokemons: MutableLiveData<PokemonResponse> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()

    fun getPokemons(){
        repository.requestPokemon({
            pokemons.postValue(it)
        },{
            error.postValue(it)
        })
    }

}