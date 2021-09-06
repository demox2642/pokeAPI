package com.skilbox.mypokemons

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.skilbox.flowsearchmovie.ViewBindingFragment
import com.skilbox.mypokemons.adapter.PokemonViewAdapter
import com.skilbox.mypokemons.data.Favorite
import com.skilbox.mypokemons.data.FavoritePokemons
import com.skilbox.mypokemons.databinding.FragmentRandomBinding
import com.skilbox.mypokemons.plagin.viewmodel.PokemonViewModel

class RandomFragment : ViewBindingFragment<FragmentRandomBinding>(FragmentRandomBinding::inflate) {

    private val viewModel: PokemonViewModel by viewModels()

    private var movieAdapter: PokemonViewAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initList()
        bindViewModel()
        viewModel.getRandomPokemon()
    }

    private fun bindViewModel() {
        viewModel.favoritePokemonokemonList.observe(viewLifecycleOwner) {
            movieAdapter?.items = it
        }
        viewModel.errorMessage.observe(viewLifecycleOwner, ::showMessage)
    }

    private fun initList() {
        movieAdapter = PokemonViewAdapter { clickItem -> chengeFavoriteStatus(clickItem) }
        with(binding.randomPokemonView) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun chengeFavoriteStatus(clickItem: FavoritePokemons) {
        if (clickItem.itFavorite) {
            viewModel.deleteFromFavorite(clickItem.id)
        } else {
            viewModel.addFavorite(Favorite(null, clickItem.id))
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
