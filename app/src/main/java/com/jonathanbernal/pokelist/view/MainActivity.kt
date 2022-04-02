package com.jonathanbernal.pokelist.view

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jonathanbernal.pokelist.R
import com.jonathanbernal.pokelist.databinding.ActivityMainBinding
import com.jonathanbernal.pokelist.service.UpdateReceiver
import com.jonathanbernal.pokelist.viewmodel.PokeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val pokeViewModel: PokeViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    lateinit var connection: UpdateReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connection = UpdateReceiver()

        setupBinding()

        observerLiveData()

    }

    override fun onStart() {
        super.onStart()
        registerReceiver(connection, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setupBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.pokeList = pokeViewModel
        pokeViewModel.getPokemons()
        binding.root
    }


    private fun observerLiveData() {
        pokeViewModel.pokemons.observe(this, Observer {
            pokeViewModel.setPokemonsInRecyclerAdapter(it)
        })
    }


    override fun onStop() {
        super.onStop()
        unregisterReceiver(connection)
    }

}
