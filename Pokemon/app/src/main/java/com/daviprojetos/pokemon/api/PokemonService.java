package com.daviprojetos.pokemon.api;

import com.daviprojetos.pokemon.model.Pokemon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonService {

    //Para o formato Json
    @GET("/posts")
    Call <List<Pokemon>>recuperarPokemons();
}
