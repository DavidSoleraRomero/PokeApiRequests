package com.example.pokedex.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.IPokemonRepository
import com.example.pokedex.data.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val defaultPokemonRepository: IPokemonRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<PokemonListUiState>(PokemonListUiState.Loading)
    val uiState: StateFlow<PokemonListUiState>
        get() = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                defaultPokemonRepository.pokemonListEmitter.collect {
                        pokemonList ->
                    if (pokemonList.isNotEmpty())
                        _uiState.value = PokemonListUiState.Items(pokemonList)
                    else
                        _uiState.value = PokemonListUiState.Loading
                }
            }
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                defaultPokemonRepository.readPaginated()
            }
        }

    }

}

sealed class PokemonListUiState() {
    data object Loading: PokemonListUiState()
    class Items(val pokemonList: List<Pokemon>): PokemonListUiState()
    class Error(val errorMessage: String): PokemonListUiState()
}