package com.skilbox.mypokemons.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skilbox.mypokemons.database.PokemonContract

@Entity(tableName = PokemonContract.TABLE_NAME)
data class Pokemon(
    @PrimaryKey()
    @ColumnInfo(name = PokemonContract.Colums.ID)
    val id: Int,
    @ColumnInfo(name = PokemonContract.Colums.NAME)
    val name: String,
    @ColumnInfo(name = PokemonContract.Colums.WEIGHT)
    val weight: Int,
    @ColumnInfo(name = PokemonContract.Colums.HEIGHT)
    val height: Int,
    @ColumnInfo(name = PokemonContract.Colums.SPRITES)
    val sprites: Sprites
)
