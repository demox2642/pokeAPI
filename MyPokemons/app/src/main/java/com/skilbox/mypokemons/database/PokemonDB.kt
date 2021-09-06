package com.skilbox.mypokemons.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.skilbox.mypokemons.data.Favorite
import com.skilbox.mypokemons.data.Pokemon
import com.skilbox.mypokemons.database.PokemonDB.Companion.DB_VERSION

@Database(
    entities = [
        Pokemon::class,
        Favorite::class
    ],
    version = DB_VERSION
)

@TypeConverters(SpritesConverter::class)

abstract class PokemonDB : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "PokemonDB"
    }
}
