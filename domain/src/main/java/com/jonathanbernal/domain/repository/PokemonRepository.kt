package com.jonathanbernal.domain.repository

import com.jonathanbernal.domain.model.PokemonAbilitiesResponse
import com.jonathanbernal.domain.model.PokemonResponse
import io.reactivex.Observable

interface PokemonRepository {
    fun getPokeList(): Observable<PokemonResponse>
    fun getPokemonData(name:String):Observable<PokemonAbilitiesResponse>
}