package com.jonathanbernal.pokelist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jonathanbernal.pokelist.R
import com.jonathanbernal.pokelist.model.Pokemon

class PokemonAdapter :RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    var pokemonList = ArrayList<Pokemon>()

    fun addPokemons(pokemonList: MutableList<Pokemon>) {
        this.pokemonList.addAll(pokemonList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.name.text = pokemon.name
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        internal var name: TextView
        init {
            name = itemView.findViewById(R.id.name_pokemon)
        }
    }
}
