package com.example.pokedex.data.remote

import com.example.pokedex.data.Pokemon
import retrofit2.Response

interface IRemotePokemonRepository {

    suspend fun readPaginated(): Response<PaginatedPokeApiResponse>
    suspend fun readOne(id: Int): Response<Pokemon>
    suspend fun readOneByUrl(url: String): Response<Pokemon>

}