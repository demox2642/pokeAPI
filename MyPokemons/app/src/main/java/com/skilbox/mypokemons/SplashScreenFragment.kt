package com.skilbox.mypokemons

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.skilbox.flowsearchmovie.ViewBindingFragment
import com.skilbox.mypokemons.databinding.FragmentSplashScreenBinding
import com.skilbox.mypokemons.plagin.viewmodel.PokemonViewModel

class SplashScreenFragment : ViewBindingFragment<FragmentSplashScreenBinding>(FragmentSplashScreenBinding::inflate) {

    private val viewModel: PokemonViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.gelAllPokemons()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
    }

    private fun bindViewModel() {
        viewModel.loadinStatus.observe(viewLifecycleOwner, ::checkStatus)
    }

    private fun checkStatus(status: Boolean) {
        if (!status) {
            findNavController().navigate(R.id.action_splashScreenFragment_to_mainFragment)
        }
    }
}
