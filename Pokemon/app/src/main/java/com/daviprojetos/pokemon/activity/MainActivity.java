package com.daviprojetos.pokemon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.GridView;

import com.daviprojetos.pokemon.R;
import com.daviprojetos.pokemon.adapter.PokedexAdapter;
import com.daviprojetos.pokemon.api.PokemonService;
import com.daviprojetos.pokemon.model.Pokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private List<Pokemon>listaPokemons = new ArrayList<>();
    private List<Pokemon>listaString = new ArrayList<>();
    private PokemonService service;
    private Retrofit retrofit;
    private String urlApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        //Realizando configuração da requisição
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(PokemonService.class);

        //Informações do GridView
        GridView gridPokedex = findViewById(R.id.gridPokedex);

        //Configurar Adapter
        PokedexAdapter adapter = new PokedexAdapter(listaPokemons,this);
        gridPokedex.setAdapter(adapter);*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        //recuperarListaPokemon();
        recuperandoDados();
    }

    private void recuperandoDados(){
        MyTask task = new MyTask();
        urlApi ="https://raw.githubusercontent.com/Biuni/PokemonGO-Pokedex/master/pokedex.json";
        task.execute(urlApi);
    }


    private void recuperarListaPokemon(){
        Call<List<Pokemon>>call  = service.recuperarPokemons();

        call.enqueue(new Callback<List<Pokemon>>() {
            @Override
            public void onResponse(Call<List<Pokemon>> call, Response<List<Pokemon>> response) {
                if(response.isSuccessful()){
                    listaPokemons = response.body();
                    for(int i = 0;i<listaPokemons.size();i++){
                        Pokemon pokemon = listaPokemons.get(i);
                        System.out.println("Resultado: "+pokemon.getId());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Pokemon>> call, Throwable t) {

            }
        });
    }

    class MyTask extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String stringUrl = strings[0];
            InputStream inputStream ;
            InputStreamReader inputStreamReader;
            StringBuffer buffer = null;//Variável para montar todos os caracteres em uma string

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                //Recupera os dados em Bytes
                inputStream = conexao.getInputStream();

                //inputStreamReader lê os dados em Bytes e decodifica para caracteres
                inputStreamReader = new InputStreamReader(inputStream);

                //Usado para a leitura dos caracteres do InputStreamReader
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";
                while((linha = reader.readLine()) != null ){

                    buffer.append(linha);
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }catch (NullPointerException npe){
                System.out.println("Erro: "+npe);
            }

            return buffer.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Pokemon pokemon = new Pokemon();
            JSONArray objetoPokemon = null;
            List<String>tipos=new ArrayList<>();
            List<String>fraquezas=new ArrayList<>();

            try {
                JSONObject jsonObject = new JSONObject(s);
                objetoPokemon = jsonObject.getJSONArray("pokemon");

                for(int i =0;i<objetoPokemon.length();i++){
                    tipos.clear();
                    fraquezas.clear();
                    JSONObject jsonObjectDadosPokemon = new JSONObject(objetoPokemon.getString(i));

                    pokemon.setId(jsonObjectDadosPokemon.getInt("id"));
                    pokemon.setNum(jsonObjectDadosPokemon.getString("num"));
                    pokemon.setName(jsonObjectDadosPokemon.getString("name"));
                    pokemon.setImg(jsonObjectDadosPokemon.getString("img"));
                    pokemon.setHeight(jsonObjectDadosPokemon.getString("height"));
                    pokemon.setWeight(jsonObjectDadosPokemon.getString("weight"));
                    //Pegando os Tipos do Pokemon
                    JSONArray tipo = jsonObjectDadosPokemon.getJSONArray("type");
                    for(int j=0;j<tipo.length();j++){
                        String valor = tipo.getString(j);
                        tipos.add(valor);
                        pokemon.setType(tipos);

                    }

                    //Pegando as fraquezas
                    JSONArray fraqueza = jsonObjectDadosPokemon.getJSONArray("weaknesses");
                    for(int k = 0; k<fraqueza.length();k++){
                        String valor = fraqueza.getString(k);
                        fraquezas.add(valor);
                        pokemon.setWeaknesses(fraquezas);
                    }

                    //pokemon.setWeaknesses(jsonObjectDadosPokemon.getString("weaknesses"));

                    System.out.println("ID POKEMON: "+pokemon.getId() +" Número Pokemon: "+ pokemon.getNum()+ " Nome: "+pokemon.getName()
                    +" Endereço da imagem: "+pokemon.getImg()+" Tamanho: "+pokemon.getHeight()+" Peso: "+pokemon.getWeight()+"    TIPOS: "+pokemon.getType()+"   FRAQUEZAS: "+ pokemon.getWeaknesses());
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}