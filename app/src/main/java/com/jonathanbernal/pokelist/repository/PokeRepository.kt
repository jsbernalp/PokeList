package com.jonathanbernal.pokelist.repository

import com.jonathanbernal.pokelist.model.PokemonResponse
import com.jonathanbernal.pokelist.network.ApiObserver
import com.jonathanbernal.pokelist.network.PokeApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private var pokeApiService: PokeApiService,
    private var compositeDisposable: CompositeDisposable
){
    fun requestPokemon(onResult:(PokemonResponse)->Unit,onError:(Throwable) -> Unit){
        pokeApiService.getPokeList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiObserver<PokemonResponse>(compositeDisposable){
                override fun onApiSuccess(data: PokemonResponse) {
                    onResult(data)
                }
                override fun onApiError(er: Throwable) {
                    onError(er)
                }
            })
    }

    fun onDestroy(){
        compositeDisposable.clear()
    }

}