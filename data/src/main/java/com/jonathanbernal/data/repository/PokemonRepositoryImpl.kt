package com.jonathanbernal.data.repository

import com.jonathanbernal.data.PokemonApi
import com.jonathanbernal.data.mapper.PokemonMapper
import com.jonathanbernal.domain.model.Pokemon
import com.jonathanbernal.domain.model.PokemonResponse
import com.jonathanbernal.domain.repository.PokemonRepository
import io.reactivex.Observable
import io.reactivex.Single

class PokemonRepositoryImpl(
    private val pokemonApi: PokemonApi,
    private val pokemonMapper: PokemonMapper
) : PokemonRepository{
    override fun getPokeList(): Observable<PokemonResponse> {
        return pokemonApi.getPokemonList()

    }

}