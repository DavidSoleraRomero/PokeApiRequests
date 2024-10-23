package com.example.pokedex.data.remote

import com.example.pokedex.data.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokeApiService {

    @GET("pokemon")
    suspend fun readPaginated(): Response<PaginatedPokeApiResponse>

    @GET("pokemon/{id}")
    suspend fun readOne(@Path("id") id: Int): Response<Pokemon>

    @GET
    suspend fun readOneByUrl(@Url url: String): Response<Pokemon>

}