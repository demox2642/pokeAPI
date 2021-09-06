package com.skilbox.mypokemons.data

data class FavoritePokemons(
    val id: Int,
    val name: String,
    val weight: Int,
    val height: Int,
    val sprites: Sprites,
    val itFavorite: Boolean
)
