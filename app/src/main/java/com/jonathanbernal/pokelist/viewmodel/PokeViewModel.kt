package com.jonathanbernal.pokelist.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathanbernal.pokelist.R
import com.jonathanbernal.pokelist.adapter.PokemonAdapter
import com.jonathanbernal.domain.model.Pokemon
import com.jonathanbernal.domain.usecase.GetPokemonListUseCase
import com.jonathanbernal.domain.usecase.GetPokemonUseCase
import com.jonathanbernal.pokelist.ext.addTo
import com.jonathanbernal.pokelist.repository.PokeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PokeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonUseCase: GetPokemonUseCase,
    private val repository: PokeRepository,
    private val context: Context):ViewModel(){

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

    fun getPokemonData(name:String){
        getPokemonUseCase.execute(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleGetPokemonResult(it) }
            .addTo(disposables)
    }

    private fun handleGetPokemonResult(result: GetPokemonUseCase.Result?) {
        when(result){
            is GetPokemonUseCase.Result.Success-> {
                Log.e("PokemonViewModel", "pokemon ${result.pokemon.sprites}")
            }
            is GetPokemonUseCase.Result.Failure -> {
                Log.e("PokemonViewModel", "pokemon error ${result.throwable}")
            }
            else -> {}
        }
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


    fun setPokemonsInRecyclerAdapter(pokemons: List<Pokemon>) {
        adapter?.setPokemonList(pokemons)
    }

    fun getRecyclerPokemonAdapter(): PokemonAdapter?{
        adapter = PokemonAdapter(this, R.layout.cell_item_pokemon)
        return  adapter
    }

    fun getPokemonAt(position:Int): Pokemon?{
        val pokemons: MutableLiveData<List<Pokemon>> = pokemons
        return pokemons.value?.get(position)
    }

    fun onClickItem(position: Int){
        val pokemons: MutableLiveData<List<Pokemon>> = pokemons
        val pokemonItem = pokemons.value?.get(position)
        pokemonItem?.let { getPokemonData(it) }
    }

    private fun getPokemonData(pokemonItem: Pokemon) {
        Toast.makeText(context,"Datos del pokemon seleccionado ${pokemonItem.name}",Toast.LENGTH_LONG).show()
        getPokemonData(pokemonItem.name)
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

}