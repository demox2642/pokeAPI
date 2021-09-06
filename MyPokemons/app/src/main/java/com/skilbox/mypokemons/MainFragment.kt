package com.skilbox.mypokemons

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.skilbox.flowsearchmovie.ViewBindingFragment
import com.skilbox.mypokemons.adapter.PokemonViewAdapter
import com.skilbox.mypokemons.data.Favorite
import com.skilbox.mypokemons.data.FavoritePokemons
import com.skilbox.mypokemons.databinding.FragmentMainBinding
import com.skilbox.mypokemons.plagin.viewmodel.PokemonViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : ViewBindingFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: PokemonViewModel by viewModels()

    private var movieAdapter: PokemonViewAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavoritePokemon()
        initToolBar()
        bindViewModel()
        initList()
    }

    private fun bindViewModel() {
        viewModel.favoritePokemonokemonList.observe(viewLifecycleOwner) {
            movieAdapter?.items = it
            checkList(it.isEmpty())
        }
        viewModel.errorMessage.observe(viewLifecycleOwner, ::showMessage)
    }

    private fun initToolBar() {
        my_toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search_pokemon -> {
                    findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
                    true
                }
                R.id.random_pokemon -> {
                    findNavController().navigate(R.id.action_mainFragment_to_randomFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun initList() {

        movieAdapter = PokemonViewAdapter { clickItem -> chengeFavoriteStatus(clickItem) }
        with(binding.favoriteListView) {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun checkList(isempty: Boolean) {

        binding.favoriteListView.isVisible = isempty.not()
        binding.emptyFavoritePokemonMessage.isVisible = isempty
    }

    private fun chengeFavoriteStatus(clickItem: FavoritePokemons) {
        if (clickItem.itFavorite) {
            viewModel.deleteFromFavorite(clickItem.id)
        } else {
            viewModel.addFavorite(Favorite(null, clickItem.id))
        }
        viewModel.getFavoritePokemon()
    }

    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
