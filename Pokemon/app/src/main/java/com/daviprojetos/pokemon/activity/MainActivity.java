package com.daviprojetos.pokemon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.daviprojetos.pokemon.R;
import com.daviprojetos.pokemon.adapter.PokedexAdapter;
import com.daviprojetos.pokemon.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

import me.sargunvohra.lib.pokekotlin.client.PokeApi;
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient;
import me.sargunvohra.lib.pokekotlin.model.PokemonSpecies;

public class MainActivity extends AppCompatActivity {

    private List<Pokemon>listaPokemons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Informações do GridView
        GridView gridPokedex = findViewById(R.id.gridPokedex);

        //Configurar Adapter
        PokedexAdapter adapter = new PokedexAdapter(listaPokemons,this);
        gridPokedex.setAdapter(adapter);


    }

}