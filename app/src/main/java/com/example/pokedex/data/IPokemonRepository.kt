package com.example.pokedex.data

interface IPokemonRepository {

    suspend fun readPaginated(): List<Pokemon>
    suspend fun readOne(id: Int): Pokemon

}