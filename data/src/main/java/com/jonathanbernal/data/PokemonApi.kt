package com.jonathanbernal.data

import com.jonathanbernal.domain.model.Pokemon
import com.jonathanbernal.domain.model.PokemonResponse
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class PokemonApi @Inject constructor(private val pokemonEndPoint: PokemonEndPoint){

    fun getPokemonList(): Observable<PokemonResponse> {
        return pokemonEndPoint.getPokeList()
    }

}