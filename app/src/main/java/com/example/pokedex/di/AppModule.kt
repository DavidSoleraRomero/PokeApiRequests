package com.example.pokedex.di

import android.content.Context
import androidx.room.Room
import com.example.pokedex.data.DefaultPokemonRepository
import com.example.pokedex.data.IPokemonRepository
import com.example.pokedex.data.local.ILocalPokemonRepository
import com.example.pokedex.data.local.LocalPokemonRepository
import com.example.pokedex.data.local.database.PokemonDao
import com.example.pokedex.data.local.database.PokemonDataBase
import com.example.pokedex.data.remote.IRemotePokemonRepository
import com.example.pokedex.data.remote.PokeApiService
import com.example.pokedex.data.remote.RemotePokemonRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideDefaultPokemonRepository(defaultPokemonRepository: DefaultPokemonRepository): IPokemonRepository

    @Binds
    abstract fun provideRemotePokemonRepository(remotePokemonRepository: RemotePokemonRepository): IRemotePokemonRepository

    @Binds
    abstract fun provideLocalPokemonRepository(localPokemonRepository: LocalPokemonRepository): ILocalPokemonRepository

}

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providePokeApiServiceClass(): PokeApiService {
        val pokeApiUrl = "https://pokeapi.co/api/v2/"
        return Retrofit.Builder()
            .baseUrl(pokeApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokeApiService::class.java)
    }

}

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun providePokemonDataBase(@ApplicationContext context: Context): PokemonDataBase {
        return Room.databaseBuilder(context,
                                    PokemonDataBase::class.java,
                                    "pokemon-db").build()
    }

    @Provides
    fun providePokemonDao(pokemonDataBase: PokemonDataBase): PokemonDao {
        return pokemonDataBase.pokemonDao()
    }

}