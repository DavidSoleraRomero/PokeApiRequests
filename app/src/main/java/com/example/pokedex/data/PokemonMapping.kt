package com.example.pokedex.data

import com.example.pokedex.data.local.database.PokemonEntity

fun PokemonEntity.toPokemon(): Pokemon {
    return Pokemon(
        id,
        name,
        Sprites(front_default)
    )
}

fun List<PokemonEntity>.toPokemon(): List<Pokemon> = map(PokemonEntity::toPokemon)

fun Pokemon.toEntity(): PokemonEntity {
    return PokemonEntity(
        id,
        name,
        sprites.front_default
    )
}

fun List<Pokemon>.toEntity(): List<PokemonEntity> = map(Pokemon::toEntity)