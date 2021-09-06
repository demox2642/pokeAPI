package com.skilbox.mypokemons.adapter

import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.skilbox.flowsearchmovie.adapter.inflate
import com.skilbox.mypokemons.R
import com.skilbox.mypokemons.data.FavoritePokemons
class PokemonDelegateAdapter(
    val onItemClick: (item: FavoritePokemons) -> Unit
) :
    AbsListItemAdapterDelegate<FavoritePokemons, FavoritePokemons, PokemonDelegateAdapter.PokemonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): PokemonViewHolder {
        return PokemonViewHolder(parent.inflate(R.layout.pokemon_item), onItemClick)
    }

    class PokemonViewHolder(
        view: View,
        onItemClick: (item: FavoritePokemons) -> Unit
    ) : BaseHolder(view, onItemClick) {

        fun bind(pokemon: FavoritePokemons) {
            bindMainInfo(id = pokemon.id, favorite = pokemon.itFavorite, name = pokemon.name, weight = pokemon.weight, height = pokemon.height, sprites = pokemon.sprites.front_default)
        }
        override val containerView: View
            get() = itemView
    }

    override fun isForViewType(
        item: FavoritePokemons,
        items: MutableList<FavoritePokemons>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: FavoritePokemons,
        holder: PokemonViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }
}
