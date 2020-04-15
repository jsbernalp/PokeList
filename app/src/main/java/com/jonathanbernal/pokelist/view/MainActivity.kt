package com.jonathanbernal.pokelist.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jonathanbernal.pokelist.R
import com.jonathanbernal.pokelist.model.PokemonResponse
import com.jonathanbernal.pokelist.viewmodel.PokeViewModel
import com.jonathanbernal.pokelist.viewmodel.PokemonViewModelFactory
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(),HasSupportFragmentInjector{

    @Inject
    lateinit var dispatchingAndroidInjector : DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var pokemonViewModelFactory: PokemonViewModelFactory

    @Inject
    lateinit var pokeViewModel: PokeViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokeViewModel = ViewModelProviders.of(this,pokemonViewModelFactory).get(pokeViewModel::class.java)

        pokeViewModel.pokemons.observe(this,Observer<PokemonResponse>{
            val random = (0..it.results.size).random()
            Log.e("MainActivity","pokemons ${it.results[random].name}")
            textView_name.text = it.results[random].name

        })
    }


    override fun supportFragmentInjector() = dispatchingAndroidInjector


}
