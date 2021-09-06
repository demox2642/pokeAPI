package com.skilbox.mypokemons.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.skilbox.mypokemons.data.FavoritePokemons

class PokemonViewAdapter(
    private val onItemClick: (item: FavoritePokemons) -> Unit
) : AsyncListDifferDelegationAdapter<FavoritePokemons>(PokemonDiffUtilCallBack()) {

    init {
        delegatesManager.addDelegate(PokemonDelegateAdapter(onItemClick))
    }

    class PokemonDiffUtilCallBack : DiffUtil.ItemCallback<FavoritePokemons>() {
        override fun areItemsTheSame(oldItem: FavoritePokemons, newItem: FavoritePokemons): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoritePokemons, newItem: FavoritePokemons): Boolean {
            return oldItem == newItem
        }
    }
}
