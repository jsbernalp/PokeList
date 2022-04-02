package com.jonathanbernal.data.repository

import com.jonathanbernal.data.PokemonApi
import com.jonathanbernal.domain.model.PokemonAbilitiesResponse
import com.jonathanbernal.domain.model.PokemonResponse
import com.jonathanbernal.domain.repository.PokemonRepository
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi
) : PokemonRepository{

    override fun getPokeList(): Observable<PokemonResponse> {
        return pokemonApi.getPokemonList()

    }

    override fun getPokemonData(name:String): Observable<PokemonAbilitiesResponse> {
        return pokemonApi.getPokemonData(name)
    }

}