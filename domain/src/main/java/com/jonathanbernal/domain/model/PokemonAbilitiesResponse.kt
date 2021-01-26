package com.jonathanbernal.domain.model

import com.squareup.moshi.Json


data class PokemonAbilitiesResponse(
    @Json(name = "base_experience")
    val base_experience: Int,
    @Json(name = "height")
    val height:  Int,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name:String,
    @Json(name = "sprites")
    val sprites: Sprites
)