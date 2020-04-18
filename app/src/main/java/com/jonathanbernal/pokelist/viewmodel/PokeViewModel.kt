package com.jonathanbernal.pokelist.viewmodel

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jonathanbernal.pokelist.R
import com.jonathanbernal.pokelist.adapter.PokemonAdapter
import com.jonathanbernal.pokelist.model.Pokemon
import com.jonathanbernal.pokelist.model.PokemonResponse
import com.jonathanbernal.pokelist.repository.PokeRepository
import javax.inject.Inject

class PokeViewModel @Inject constructor(
    private val repository: PokeRepository):ViewModel(){

    var pokemons: MutableLiveData<PokemonResponse> = MutableLiveData()
    var error: MutableLiveData<Throwable> = MutableLiveData()
    var adapter: PokemonAdapter? = null

    fun getPokemons(){
        repository.requestPokemon({
            pokemons.postValue(it)
        },{
            error.postValue(it)
        })
    }

    fun setPokemonsInRecyclerAdapter(pokemons: List<Pokemon>) {
        adapter?.setPokemonList(pokemons)
        adapter?.notifyDataSetChanged()
    }

    fun getRecyclerPokemonAdapter(): PokemonAdapter?{
        adapter = PokemonAdapter(this, R.layout.item_pokemon)
        return  adapter
    }

    fun getPokemonAt(position:Int):Pokemon?{
        val pokemons: List<Pokemon> = pokemons.value!!.results
        return pokemons[position]
    }

    override fun onCleared() {
        super.onCleared()
        repository.onDestroy()
    }

}