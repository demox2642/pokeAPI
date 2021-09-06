package com.skilbox.mypokemons.database

import androidx.room.*
import com.skilbox.mypokemons.data.Pokemon

@Dao
interface PokemonDao {
    @Insert()
    suspend fun insertPokemons(pokemons: Pokemon)
    @Delete
    suspend fun deletePokemons(pokemon: Pokemon)
    @Query("select * from ${PokemonContract.TABLE_NAME}")
    suspend fun getPokemons(): List<Pokemon>
    @Query("select * from ${PokemonContract.TABLE_NAME} ")
    suspend fun getFavoritePokemons(): List<Pokemon>
}
