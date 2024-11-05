package com.example.pokedex.data.local

import com.example.pokedex.data.Pokemon
import com.example.pokedex.data.local.database.PokemonDao
import com.example.pokedex.data.toEntity
import com.example.pokedex.data.toPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalPokemonRepository @Inject constructor(
    private val pokemonDao: PokemonDao
): ILocalPokemonRepository {

    override suspend fun insertOne(pokemon: Pokemon) {
        pokemonDao.insertOne(pokemon.toEntity())
    }

    override suspend fun insertAll(pokemonList: List<Pokemon>) {
        pokemonDao.insertAll(pokemonList.toEntity())
    }

    override suspend fun readAll(): List<Pokemon> {
        return pokemonDao.readAll().toPokemon()
    }

    override fun observeAll(): Flow<List<Pokemon>> {
        return pokemonDao.observeAll().map { pokemon -> pokemon.toPokemon() }
    }

}