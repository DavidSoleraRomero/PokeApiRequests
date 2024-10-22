package com.example.pokedex.data

import com.example.pokedex.data.remote.IRemotePokemonRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultPokemonRepository @Inject constructor(
    private val remotePokemonRepository: IRemotePokemonRepository
): IPokemonRepository {

    override suspend fun readPaginated(): List<Pokemon> {
        val response = remotePokemonRepository.readPaginated()
        val pokemonList = mutableListOf<Pokemon>()
        if (response.isSuccessful) {
            val pokemonRawList = response.body()!!.results
            pokemonRawList.forEach {
                pRaw -> pokemonList.add(readOne(idFromUrl(pRaw.url)!!))
            }
        }
        return pokemonList
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