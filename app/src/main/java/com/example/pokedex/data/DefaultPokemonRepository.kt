package com.example.pokedex.data

import com.example.pokedex.data.remote.IRemotePokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPokemonRepository @Inject constructor(
    private val remotePokemonRepository: IRemotePokemonRepository
): IPokemonRepository {

    private val _pokemonListEmitter = MutableStateFlow<List<Pokemon>>(listOf())
    override val pokemonListEmitter: StateFlow<List<Pokemon>>
        get() = _pokemonListEmitter.asStateFlow()

    override suspend fun readPaginated() {
        val response = remotePokemonRepository.readPaginated()
        val pokemonList = _pokemonListEmitter.value.toMutableList()
        if (response.isSuccessful) {
            val pokemonRawList = response.body()!!.results
            pokemonRawList.forEach {
                pRaw ->
                pokemonList.add(readOne(idFromUrl(pRaw.url)!!))
                _pokemonListEmitter.emit(pokemonList.toList())
            }
        }
        else _pokemonListEmitter.value = pokemonList
    }

    override suspend fun readOne(id: Int): Pokemon {
        val response = remotePokemonRepository.readOne(id)
        return if (response.isSuccessful) response.body()!!
        else Pokemon(0, "", Sprites(""))
    }

    private fun idFromUrl(url: String): Int? {
        val newUrl = url.trimEnd('/')
        return newUrl.substringAfterLast('/').toIntOrNull()
    }

}