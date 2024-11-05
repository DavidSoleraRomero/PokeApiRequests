package com.example.pokedex.data

import com.example.pokedex.data.local.ILocalPokemonRepository
import com.example.pokedex.data.local.LocalPokemonRepository
import com.example.pokedex.data.remote.IRemotePokemonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPokemonRepository @Inject constructor(
    private val remotePokemonRepository: IRemotePokemonRepository,
    private val localPokemonRepository: ILocalPokemonRepository
): IPokemonRepository {

    private val _pokemonListEmitter = MutableStateFlow<List<Pokemon>>(listOf())
    override val pokemonListEmitter: StateFlow<List<Pokemon>>
        get() = _pokemonListEmitter.asStateFlow()

    override suspend fun readPaginated() {
        _pokemonListEmitter.emit(localPokemonRepository.readAll().toMutableList())
        val response = remotePokemonRepository.readPaginated()
        if (response.isSuccessful) {
            val pokemonList: MutableList<Pokemon> = mutableListOf()
            response.body()!!.results.forEach { pokemonList.add(readOneByUrl(it.url)) }
            localPokemonRepository.insertAll(pokemonList)
            _pokemonListEmitter.emit(pokemonList.toList())
        }
    }

    override suspend fun readOne(id: Int): Pokemon {
        val response = remotePokemonRepository.readOne(id)
        return if (response.isSuccessful) response.body()!!
        else Pokemon(0, "", Sprites(""))
    }

    override suspend fun readOneByUrl(url: String): Pokemon {
        val response = remotePokemonRepository.readOneByUrl(url)
        return if (response.isSuccessful) response.body()!!
        else Pokemon(0, "", Sprites(""))
    }

}