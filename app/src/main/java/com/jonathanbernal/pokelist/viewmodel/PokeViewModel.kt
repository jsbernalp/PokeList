package com.jonathanbernal.pokelist.viewmodel

import android.annotation.SuppressLint
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
import com.jonathanbernal.domain.model.PokemonAbilitiesResponse
import com.jonathanbernal.domain.usecase.GetPokemonListUseCase
import com.jonathanbernal.domain.usecase.GetPokemonUseCase
import com.jonathanbernal.pokelist.ext.addTo
import com.jonathanbernal.pokelist.repository.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class PokeViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonUseCase: GetPokemonUseCase,
    private val repository: PokeRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    var pokemons: MutableLiveData<List<Pokemon>> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()
    var adapter: PokemonAdapter? = null

    val disposables = CompositeDisposable()
    val progressVisible = ObservableBoolean()
    var pokemonList = ObservableArrayList<Pokemon>()


    fun getPokemons() {
        getPokemonListUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleGetPokemonListResult(it) }
            .addTo(disposables)
    }

    fun getPokemonData(pokemon: Pokemon, position: Int) {
        getPokemonUseCase.execute(pokemon.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { handleGetPokemonResult(it, pokemon, position) }
            .addTo(disposables)
    }

    private fun handleGetPokemonResult(
        result: GetPokemonUseCase.Result?,
        pokemon: Pokemon,
        position: Int
    ) {
        when (result) {
            is GetPokemonUseCase.Result.Success -> {
                updateItem(result.pokemon as PokemonAbilitiesResponse, pokemon, position)
                Log.e("PokemonViewModel", "pokemon ${result.pokemon.sprites}")
            }
            is GetPokemonUseCase.Result.Failure -> {
                Log.e("PokemonViewModel", "pokemon error ${result.throwable}")
            }
            else -> {}
        }
    }

    private fun handleGetPokemonListResult(result: GetPokemonListUseCase.Result?) {
        when (result) {
            is GetPokemonListUseCase.Result.Success -> {
                pokemons.postValue(result.pokemons)
            }
            is GetPokemonListUseCase.Result.Failure -> {
                Log.e("PokeViewModel", "error ${result.throwable}")
            }
        }
    }

    fun updateItem(
        pokemonAbilitiesResponse: PokemonAbilitiesResponse,
        pokemon: Pokemon,
        position: Int
    ) {
        val url = pokemonAbilitiesResponse.sprites.other.dream_world.front_default.replace(
            "/dream-world/",
            "/official-artwork/"
        ).replace(".svg", ".png")
        val pokemonData = Pokemon(pokemonAbilitiesResponse.name, pokemon.url, url)
        adapter?.updatePokemon(pokemonData)
        adapter?.notifyItemChanged(position)
    }

    fun setPokemonsInRecyclerAdapter(pokemons: List<Pokemon>) {
        adapter?.setPokemonList(pokemons)
    }

    fun getRecyclerPokemonAdapter(): PokemonAdapter? {
        adapter = PokemonAdapter(this, R.layout.cell_item_pokemon)
        return adapter
    }

    fun getPokemonAt(position: Int): Pokemon? {
        val pokemons: MutableLiveData<List<Pokemon>> = pokemons
        return pokemons.value?.get(position)
    }


    fun getImagePokemon(position: Int): Pokemon? {
        val pokemons: MutableLiveData<List<Pokemon>> = pokemons
        return pokemons.value?.get(position)
    }

    fun onClickItem(position: Int) {
        val pokemons: MutableLiveData<List<Pokemon>> = pokemons
        val pokemonItem = pokemons.value?.get(position)
        pokemonItem?.let { getPokemon(it, position) }
    }

    private fun getPokemon(pokemonItem: Pokemon, position: Int) {
        Toast.makeText(
            context,
            "Datos del pokemon seleccionado ${pokemonItem.name}",
            Toast.LENGTH_LONG
        ).show()
        getPokemonData(pokemonItem, position)
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

}