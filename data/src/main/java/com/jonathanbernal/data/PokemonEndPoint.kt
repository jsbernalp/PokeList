package com.jonathanbernal.data

import com.jonathanbernal.domain.model.Pokemon
import com.jonathanbernal.domain.model.PokemonAbilitiesResponse
import com.jonathanbernal.domain.model.PokemonResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonEndPoint {

    @GET("pokemon")
    fun getPokeList(): Observable<PokemonResponse>


    @GET("pokemon/{name}")
    fun getPokemonData(@Path ("name") name:String): Observable<PokemonAbilitiesResponse>
}