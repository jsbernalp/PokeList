package com.jonathanbernal.domain.model

import com.jonathanbernal.domain.model.Pokemon

data class PokemonResponse(
    val results:MutableList<Pokemon>
)