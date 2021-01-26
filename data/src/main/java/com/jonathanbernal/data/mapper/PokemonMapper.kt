package com.jonathanbernal.data.mapper

import com.jonathanbernal.domain.model.Pokemon
import com.jonathanbernal.domain.model.PokemonResponse
import javax.inject.Inject

class PokemonMapper @Inject constructor(){

    fun map(responseList: PokemonResponse): List<Pokemon> {
        return responseList.results.map { map(it) }
    }

    private fun map(response: Pokemon):Pokemon {
        return Pokemon(
            name = response.name,
            url = response.url,
            urlImage = response.urlImage
        )
    }

}