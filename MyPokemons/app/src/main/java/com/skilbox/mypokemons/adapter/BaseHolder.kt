package com.skilbox.mypokemons.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.skilbox.mypokemons.R
import com.skilbox.mypokemons.data.FavoritePokemons
import com.skilbox.mypokemons.data.Sprites
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.pokemon_item.*

abstract class BaseHolder(
    view: View,
    private val onItemCkick: (item: FavoritePokemons) -> Unit
) : RecyclerView.ViewHolder(view), LayoutContainer {

    protected fun bindMainInfo(
        id: Int,
        favorite: Boolean,
        name: String,
        weight: Int,
        height: Int,
        sprites: String
    ) {

        favorite_image.setOnClickListener {
            onItemCkick(FavoritePokemons(id, name, weight, height, Sprites(sprites), favorite))
            if (!favorite) {
                favorite_image.setImageResource(R.drawable.star_24)
            } else {
                favorite_image.setImageResource(R.drawable.star_border_24)
            }
        }

        pokemon_weight.text = "Вес: $weight"
        pokemon_height.text = "Рост: $height"
        pokemon_name.text = name

        if (favorite) {
            favorite_image.setImageResource(R.drawable.star_24)
        }

        Glide.with(pokemon_image)
            .load(sprites)
            .placeholder(R.drawable.load_imege)
            .into(pokemon_image)
            .view
    }
}
