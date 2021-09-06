package com.skilbox.mypokemons.network


import com.skilbox.mypokemons.data.PokeResponse
import com.skilbox.mypokemons.data.Pokemon
import retrofit2.Call
import retrofit2.http.*

interface PokeApi {

    @GET("pokemon/{name}")
    fun getPokemonInfo(@Path("name") name: String): Call<Pokemon>

    @GET("pokemon/?limit=10&offset=0")
    fun getAllPokemons(): Call<PokeResponse>
}
