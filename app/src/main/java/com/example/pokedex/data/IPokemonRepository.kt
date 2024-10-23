package com.example.pokedex.data

import kotlinx.coroutines.flow.StateFlow

interface IPokemonRepository {

    val pokemonListEmitter: StateFlow<List<Pokemon>>

    suspend fun readPaginated()
    suspend fun readOne(id: Int): Pokemon
    suspend fun readOneByUrl(url: String): Pokemon

}