package com.example.pokedex.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentPokemonListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FragmentPokemonList : Fragment() {

    private lateinit var binding: FragmentPokemonListBinding
    private val viewModel: PokemonListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPokemonListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {

            val recyclerView = binding.pokemonList
            recyclerView.adapter = PokemonListAdapter()

            viewModel.uiState.collect {
                    uiState ->
                when (uiState) {
                    PokemonListUiState.Loading -> {}
                    is PokemonListUiState.Items -> {
                        (recyclerView.adapter as PokemonListAdapter).submitList(uiState.pokemonList)
                    }
                    is PokemonListUiState.NoItems -> {
                        // TODO()
                    }
                    is PokemonListUiState.Error -> {
                        // TODO()
                    }
                }
            }

        }

    }

}