package com.jonathanbernal.pokelist.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jonathanbernal.domain.model.Pokemon
import com.jonathanbernal.pokelist.R
import com.jonathanbernal.pokelist.databinding.ActivityMainBinding
import com.jonathanbernal.domain.model.PokemonResponse
import com.jonathanbernal.pokelist.service.UpdateReceiver
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

    private lateinit var binding: ActivityMainBinding

    lateinit var connection: UpdateReceiver



    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connection = UpdateReceiver()

        setupBinding(savedInstanceState)

        observerLiveData()

    }

    override fun onStart() {
        super.onStart()
        registerReceiver(connection, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setupBinding(savedInstanceState: Bundle?) {

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        pokeViewModel = ViewModelProviders.of(this,pokemonViewModelFactory).get(pokeViewModel::class.java)
        binding.pokeList = pokeViewModel
        pokeViewModel.getPokemons()
        binding.root
    }


    private fun observerLiveData() {
        pokeViewModel.pokemons.observe(this,Observer{
            pokeViewModel.setPokemonsInRecyclerAdapter(it)
        })
    }


    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onStop() {
        super.onStop()
        unregisterReceiver(connection)
    }

}
