package com.jonathanbernal.pokelist.network

import com.jonathanbernal.domain.model.PokemonResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface PokeApiService {

    @GET("pokemon")
    fun getPokeList(): Observable<PokemonResponse>

}