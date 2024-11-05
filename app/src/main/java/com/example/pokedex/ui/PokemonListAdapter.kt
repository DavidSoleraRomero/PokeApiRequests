package com.example.pokedex.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokedex.R
import com.example.pokedex.data.Pokemon
import com.example.pokedex.databinding.PokemonViewBinding

class PokemonListAdapter: ListAdapter<Pokemon, PokemonListAdapter.PokemonListViewHolder>(PokemonComparer) {

    inner class PokemonListViewHolder(
        private val binding: PokemonViewBinding
    ): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(pokemon: Pokemon) {
            binding.pokemonId.text = "ID ${pokemon.id}"
            binding.pokemonName.text = pokemon.name.replaceFirstChar { char -> char.uppercase() }
            binding.pokemonImage.load(pokemon.sprites.front_default) {
                placeholder(R.drawable.ic_loading)
                error(R.drawable.ic_loading)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val binding = PokemonViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PokemonListViewHolder(binding);
    }

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.bind(pokemon)
    }

    object PokemonComparer: DiffUtil.ItemCallback<Pokemon>() {

        override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem.name == newItem.name
                && oldItem.id == newItem.id

    }

}