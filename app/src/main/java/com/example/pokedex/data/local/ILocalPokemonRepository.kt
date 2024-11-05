package com.example.pokedex.data.local

import com.example.pokedex.data.Pokemon
import kotlinx.coroutines.flow.Flow

interface ILocalPokemonRepository {

    suspend fun insertOne(pokemon: Pokemon)

    suspend fun insertAll(pokemonList: List<Pokemon>)

    suspend fun readAll(): List<Pokemon>

    fun observeAll(): Flow<List<Pokemon>>

}