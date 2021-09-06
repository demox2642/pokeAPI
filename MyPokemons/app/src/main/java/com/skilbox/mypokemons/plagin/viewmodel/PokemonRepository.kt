package com.skilbox.mypokemons.plagin.viewmodel

import android.util.Log
import com.skilbox.mypokemons.network.NetworkRetrofit
import com.skilbox.mypokemons.data.*
import com.skilbox.mypokemons.database.Database
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonRepository {

    private val pokemonDao = Database.instance.pokemonDao()
    private val favoriteDao = Database.instance.favoriteDao()

    fun getPokeResult(result: (ArrayList<PokemonResult>) -> Unit) {
        return NetworkRetrofit.api.getAllPokemons().enqueue(
            object : Callback<PokeResponse> {
                override fun onResponse(
                    call: Call<PokeResponse>?,
                    response: Response<PokeResponse>?
                ) {
                    if (response!!.isSuccessful) {
                        result(response.body()!!.results)
                    } else {
                        Log.e("isSuccessful", "ERROR${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<PokeResponse>?, t: Throwable?) {
                    Log.e("onFailure", "ERROR$t")
                }
            }
        )
    }

    fun getPokemons(pokemonResult: List<PokemonResult>, pokeList: (List<Pokemon>) -> Unit) {

        val list = mutableListOf<Pokemon>()
        for (element in pokemonResult) {
            NetworkRetrofit.api.getPokemonInfo(element.name).enqueue(
                object : Callback<Pokemon> {
                    override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                        if (response.isSuccessful) {
                            list.add(response.body()!!)
                            pokeList(list)
                        } else {
                            Log.e("getPokemons is Successful", "ERROR${response.errorBody()}")
                        }
                    }

                    override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                        Log.e("getPokemons onFailure", "ERROR$t")
                    }
                }
            )
        }
    }

    suspend fun insertToDataBase(pokemonsList: Pokemon) {
        pokemonDao.insertPokemons(pokemonsList)
    }

    suspend fun getAllFavoritePokemon(): List<FavoritePokemons> {
        val list = favoriteDao.getAllFavoritePokemon()
        return list
    }

    suspend fun searchFavoritePokemon(name: String): List<FavoritePokemons> {

        return favoriteDao.searchFavoritePokemon(name)
    }

    suspend fun deleteFavoritePokemon(id: Int) {
        favoriteDao.deleteFavoritePokemon(id)
    }

    suspend fun addFavoritePokemon(favorite: Favorite) {
        favoriteDao.addFavoritePokemon(favorite)
    }

    suspend fun checkFavorite(id: Int): Int {
        return favoriteDao.checkFavorite(id)
    }
}
