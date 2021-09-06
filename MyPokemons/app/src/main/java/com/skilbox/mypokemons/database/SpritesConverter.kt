package com.skilbox.mypokemons.database

import androidx.room.TypeConverter
import com.skilbox.mypokemons.data.Sprites

class SpritesConverter {

    @TypeConverter
    fun convertSpritesToString(sprites: Sprites): String = sprites.front_default

    @TypeConverter
    fun convertStringToDate(string: String?): Sprites? {
        return if (string == null) {
            null
        } else {
            Sprites(string)
        }
    }
}
