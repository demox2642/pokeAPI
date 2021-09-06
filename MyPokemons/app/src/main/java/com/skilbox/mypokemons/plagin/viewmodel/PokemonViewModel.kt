package com.skilbox.mypokemons.plagin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.skilbox.mypokemons.data.Favorite
import com.skilbox.mypokemons.data.FavoritePokemons
import com.skilbox.mypokemons.data.Pokemon
import com.skilbox.mypokemons.database.Database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.random.Random

class PokemonViewModel : ViewModel() {

    private val repository = PokemonRepository()

    private val favoritePokemonListLiveData: MutableLiveData<List<FavoritePokemons>> = MutableLiveData()
    val favoritePokemonokemonList: LiveData<List<FavoritePokemons>>
        get() = favoritePokemonListLiveData

    private val searchPokemonListLiveData: MutableLiveData<List<FavoritePokemons>> = MutableLiveData()
    val searchPokemonokemonList: LiveData<List<FavoritePokemons>>
        get() = searchPokemonListLiveData

    private val loadinStatusLiveData = MutableLiveData<Boolean>()
    val loadinStatus: LiveData<Boolean>
        get() = loadinStatusLiveData

    private val errorMessageLiveData = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = errorMessageLiveData

    fun gelAllPokemons() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                loadinStatusLiveData.postValue(true)
                repository.getPokeResult() {
                    result ->
                    repository.getPokemons(result) {
                        pokeList ->
                        insertToDataBase(pokeList)
                    }
                }
            } catch (t: Throwable) {
                Log.e("PokemonViewModel", "$t")
            } finally {
                loadinStatusLiveData.postValue(false)
            }
        }
    }

    private fun insertToDataBase(pokemons: List<Pokemon>) {
        for (i in pokemons.indices) {
            viewModelScope.launch(Dispatchers.IO) {
                Database.instance.withTransaction {
                    try {
                        repository.insertToDataBase(pokemons[i])
                    } catch (t: Throwable) {
                        Log.e("insertToDataBase", "$t")
                    } finally {
                    }
                }
            }
        }
    }

    fun getFavoritePokemon() {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoritePokemonListLiveData.postValue(emptyList())
                val favoriteList = repository.getAllFavoritePokemon().filter { it.itFavorite }
                favoritePokemonListLiveData.postValue(favoriteList)
            } catch (t: Throwable) {
                Log.e("getFavoritePokemon", "$t")
            }
        }
    }

    fun getRandomPokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteList = repository.getAllFavoritePokemon()
            val randomInt = Random.nextInt(0, favoriteList.size - 1)

            favoritePokemonListLiveData.postValue(listOf(favoriteList[randomInt]))
        }
    }

    private var currentJob: Job? = null

    suspend fun search(nameFlow: Flow<String>) {

        currentJob = nameFlow
            .debounce(500)
            .distinctUntilChanged()
            .onEach {

                loadinStatusLiveData.postValue(true)
            }
            .mapLatest {

                repository.searchFavoritePokemon(it)
            }
            .flowOn(Dispatchers.IO)
            .onEach { it ->
                loadinStatusLiveData.value = false
                searchPokemonListLiveData.postValue(it)
            }
            .catch { it ->
                if (it !is SocketTimeoutException || it !is UnknownHostException) {
                    loadinStatusLiveData.postValue(false)
                    errorMessageLiveData.postValue("Проверьте подключение к сети интернет")
                }
            }
            .launchIn(viewModelScope)
    }

    fun deleteFromFavorite(id: Int) {
        viewModelScope.launch {
            Database.instance.withTransaction {
                try {
                    repository.deleteFavoritePokemon(id)
                } catch (t: Throwable) {
                    errorMessageLiveData.postValue("$t")
                }
            }
        }
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch {
            Database.instance.withTransaction {
                if (repository.checkFavorite(favorite.poke_id)> 0) {
                    errorMessageLiveData.postValue("Такая запись уже есть")
                } else {
                    try {

                        repository.addFavoritePokemon(favorite)
                    } catch (t: Throwable) {
                        errorMessageLiveData.postValue(t.toString())
                    }
                }
            }
        }
    }
}
