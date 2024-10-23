package com.example.pokedex.data.remote

import com.example.pokedex.data.Pokemon
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemotePokemonRepository @Inject constructor(
    private val pokeApiService: PokeApiService
): IRemotePokemonRepository {

    override suspend fun readPaginated(): Response<PaginatedPokeApiResponse> = pokeApiService.readPaginated()

    override suspend fun readOne(id: Int): Response<Pokemon> = pokeApiService.readOne(id)

    override suspend fun readOneByUrl(url: String): Response<Pokemon> = pokeApiService.readOneByUrl(url)

}