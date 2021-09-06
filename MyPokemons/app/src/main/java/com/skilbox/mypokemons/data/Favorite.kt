package com.skilbox.mypokemons.data

import androidx.room.*
import com.skilbox.mypokemons.database.FavoriteContract
import com.skilbox.mypokemons.database.PokemonContract

@Entity(
    tableName = FavoriteContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = Pokemon::class,
            parentColumns = [PokemonContract.Colums.ID],
            childColumns = [FavoriteContract.Colums.POKE_ID]
        )
    ]
)
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FavoriteContract.Colums.ID)
    val id: Long?,
    @ColumnInfo(name = FavoriteContract.Colums.POKE_ID)
    val poke_id: Int
)
