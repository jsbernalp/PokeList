package com.jonathanbernal.domain.usecase

import com.jonathanbernal.domain.model.Pokemon
import com.jonathanbernal.domain.repository.PokemonRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val pokemonRepository: PokemonRepository){

    sealed class Result {
        object Loading : Result()
        data class Success(val pokemons: List<Pokemon>) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute():Observable<Result>{
        return pokemonRepository.getPokeList()
            .map { Result.Success(it.results) as Result}
            .onErrorReturn { Result.Failure(it) }
            .startWith(Result.Loading)

    }

}