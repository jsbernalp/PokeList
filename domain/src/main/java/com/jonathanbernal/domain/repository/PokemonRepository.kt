package com.jonathanbernal.domain.repository

import com.jonathanbernal.domain.model.PokemonResponse
import io.reactivex.Observable

interface PokemonRepository {
    fun getPokeList(): Observable<PokemonResponse>
}