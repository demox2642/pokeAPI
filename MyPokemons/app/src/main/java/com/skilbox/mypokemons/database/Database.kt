package com.skilbox.mypokemons.database

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: PokemonDB

        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            PokemonDB::class.java,
            PokemonDB.DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}
