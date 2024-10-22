package com.example.pokedex.data.remote

import com.example.pokedex.data.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

    @GET("pokemon")
    suspend fun readPaginated(): Response<PaginatedPokeApiResponse>

    @GET("pokemon/{id}")
    suspend fun readOne(@Path("id") id: Int): Response<Pokemon>

}