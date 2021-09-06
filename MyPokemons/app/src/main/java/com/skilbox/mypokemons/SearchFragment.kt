package com.skilbox.mypokemons

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.skilbox.flowsearchmovie.ViewBindingFragment
import com.skilbox.flowsearchmovie.textChangedFlow
import com.skilbox.mypokemons.adapter.PokemonViewAdapter
import com.skilbox.mypokemons.data.Favorite
import com.skilbox.mypokemons.data.FavoritePokemons
import com.skilbox.mypokemons.databinding.FragmentSearchBinding
import com.skilbox.mypokemons.plagin.viewmodel.PokemonViewModel

class SearchFragment : ViewBindingFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: PokemonViewModel by viewModels()

    private var pokemonAdapter: PokemonViewAdapter? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initList()
        bindViewModel()

        lifecycleScope.launchWhenStarted {
            viewModel.search(
                binding.searchEditText.textChangedFlow()
            )
        }
    }

    private fun bindViewModel() {
        viewModel.searchPokemonokemonList.observe(viewLifecycleOwner) {
            pokemonAdapter?.items = it
        }
        viewModel.errorMessage.observe(viewLifecycleOwner, ::showMessage)
    }

    private fun initList() {
        pokemonAdapter = PokemonViewAdapter { clickItem -> chengeFavoriteStatus(clickItem) }
        with(binding.pokemonsAfterSearch) {
            adapter = pokemonAdapter
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
