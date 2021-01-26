package com.jonathanbernal.pokelist.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.caverock.androidsvg.SVG

@BindingAdapter("loadimage")
fun bindingImage(pokemonImageView: ImageView, imageUrl: String?) {
    if (imageUrl != null) {
        Glide.with(pokemonImageView.context)
            .load(imageUrl)
            .into(pokemonImageView)
    }
}