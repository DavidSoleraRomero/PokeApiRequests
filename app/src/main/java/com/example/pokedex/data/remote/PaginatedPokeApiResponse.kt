package com.example.pokedex.data.remote

data class PaginatedPokeApiResponse(
    val count: Int,
    val prev: String?,
    val next: String?,
    val results: List<PokemonRaw>
)
