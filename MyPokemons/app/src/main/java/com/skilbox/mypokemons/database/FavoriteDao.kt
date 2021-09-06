package com.skilbox.mypokemons.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.skilbox.mypokemons.data.Favorite
import com.skilbox.mypokemons.data.FavoritePokemons

@Dao
interface FavoriteDao {

    @Insert
    suspend fun addFavoritePokemon(favorite: Favorite)
    @Query("Select COUNT(*) from ${FavoriteContract.TABLE_NAME} where ${FavoriteContract.Colums.POKE_ID}=:id")
    suspend fun checkFavorite(id: Int): Int

    @Query("DELETE FROM ${FavoriteContract.TABLE_NAME}  WHERE ${FavoriteContract.Colums.POKE_ID}=:id")
    suspend fun deleteFavoritePokemon(id: Int)
    @Query(
        "   SELECT \n" +
            "             p.id,p.name,p.weight,p.height,p.sprites\n" +
            "             ,(case when f.poke_id is null then 0 else 1 end)itFavorite\n" +
            "                FROM pokemons p\n" +
            "                LEFT JOIN favorite f ON p.id=f.poke_id\n"

    )
    suspend fun getAllFavoritePokemon(): List<FavoritePokemons>
    @Query(
        "   SELECT p.id,p.name,p.weight,p.height,p.sprites,(case when f.poke_id is null then 0 else 1 end)itFavorite FROM pokemons p LEFT JOIN favorite f ON p.id=f.poke_id    WHERE p.name like '%'||:name||'%' "

    )
    suspend fun searchFavoritePokemon(name: String?): List<FavoritePokemons>
}
