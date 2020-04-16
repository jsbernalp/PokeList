package com.jonathanbernal.pokelist.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jonathanbernal.pokelist.R
import com.jonathanbernal.pokelist.adapter.PokemonAdapter
import com.jonathanbernal.pokelist.model.PokemonResponse
import com.jonathanbernal.pokelist.viewmodel.PokeViewModel
import com.jonathanbernal.pokelist.viewmodel.PokemonViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(),HasSupportFragmentInjector{

    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var pokemonViewModelFactory: PokemonViewModelFactory

    @Inject
    lateinit var pokeViewModel: PokeViewModel


    private val adapter = PokemonAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerPokemon = findViewById<RecyclerView>(R.id.recyclerPokemon)
        recyclerPokemon.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        pokeViewModel = ViewModelProviders.of(this,pokemonViewModelFactory).get(pokeViewModel::class.java)

        pokeViewModel.pokemons.observe(this,Observer<PokemonResponse>{
            adapter.addPokemons(it.results)
        })

        recyclerPokemon.adapter = adapter

    }



    override fun supportFragmentInjector() = dispatchingAndroidInjector


}
