package com.jonathanbernal.data

import com.jonathanbernal.domain.model.Pokemon
import com.jonathanbernal.domain.model.PokemonResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface PokemonEndPoint {

    @GET("pokemon")
    fun getPokeList(): Observable<PokemonResponse>

}