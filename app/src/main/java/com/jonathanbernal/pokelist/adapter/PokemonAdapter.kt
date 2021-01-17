package com.jonathanbernal.pokelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.jonathanbernal.pokelist.BR
import com.jonathanbernal.domain.model.Pokemon
import com.jonathanbernal.pokelist.viewmodel.PokeViewModel

class PokemonAdapter internal constructor(var pokeViewModel: PokeViewModel, var resource:Int):RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    protected var pokemons: List<Pokemon> = mutableListOf()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        var binding:ViewDataBinding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setPokemonCard(pokeViewModel,position)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    fun getLayoutIdForPosition(position: Int): Int {
        return resource
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    fun setPokemonList(pokemons: List<Pokemon>){
        this.pokemons = pokemons
        notifyDataSetChanged()
    }

    class ViewHolder(binding: ViewDataBinding):RecyclerView.ViewHolder(binding.root){
        private var binding:ViewDataBinding? = null
        init {
            this.binding = binding
        }
            fun setPokemonCard(pokeViewModel:PokeViewModel,position: Int){
                binding?.setVariable(BR.itemPokemon,pokeViewModel)
                binding?.setVariable(BR.position,position)
                binding?.executePendingBindings()
            }

    }
}
