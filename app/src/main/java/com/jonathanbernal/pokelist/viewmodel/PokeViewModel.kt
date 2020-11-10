package com.jonathanbernal.pokelist.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathanbernal.pokelist.R
import com.jonathanbernal.pokelist.adapter.PokemonAdapter
import com.jonathanbernal.domain.model.Pokemon
import com.jonathanbernal.domain.model.PokemonResponse
import com.jonathanbernal.domain.usecase.GetPokemonListUseCase
import com.jonathanbernal.pokelist.ext.addTo
import com.jonathanbernal.pokelist.repository.PokeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PokeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val repository: PokeRepository):ViewModel(){

    var pokemons: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()
    var adapter: PokemonAdapter? = null

    val disposables = CompositeDisposable()
    val progressVisible = ObservableBoolean()
    var pokemonList = ObservableArrayList<Pokemon>()


    fun getPokemons(){
        getPokemonListUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleGetPokemonListResult(it) }
            .addTo(disposables)
    }

    private fun handleGetPokemonListResult(result: GetPokemonListUseCase.Result?) {
        when(result){
            is GetPokemonListUseCase.Result.Success->{
                pokemons.postValue(result.pokemons)
            }
            is GetPokemonListUseCase.Result.Failure->{
                Log.e("PokeViewModel","error ${result.throwable}")
            }
        }
    }

//    fun getPokemons(){
//        repository.requestPokemon({
//            pokemons.postValue(it)
//        },{
//            error.postValue(it)
//        })
//    }

    fun setPokemonsInRecyclerAdapter(pokemons: List<Pokemon>) {
        adapter?.setPokemonList(pokemons)
        adapter?.notifyDataSetChanged()
    }

    fun getRecyclerPokemonAdapter(): PokemonAdapter?{
        adapter = PokemonAdapter(this, R.layout.item_pokemon)
        return  adapter
    }

    fun getPokemonAt(position:Int): Pokemon?{
        val pokemons: MutableLiveData<List<Pokemon>> = pokemons
        return pokemons.value?.get(position)
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

}