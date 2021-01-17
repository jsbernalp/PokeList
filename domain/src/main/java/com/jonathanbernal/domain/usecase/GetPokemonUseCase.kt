package com.jonathanbernal.domain.usecase


import com.jonathanbernal.domain.model.PokemonAbilitiesResponse
import com.jonathanbernal.domain.repository.PokemonRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    sealed class Result {
        object Loading : Result()
        data class Success(val pokemon: PokemonAbilitiesResponse) : Result()
        data class Failure(val throwable: Throwable) : Result()
    }

    fun execute(name: String): Observable<Result> {
        return pokemonRepository.getPokemonData(name)
            .map { Result.Success(it) as Result }
            .onErrorReturn { Result.Failure(it) }
            .startWith(Result.Loading)
    }

}