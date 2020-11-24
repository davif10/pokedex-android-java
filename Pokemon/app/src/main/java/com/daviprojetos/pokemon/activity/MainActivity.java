package com.daviprojetos.pokemon.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.daviprojetos.pokemon.R;
import com.daviprojetos.pokemon.adapter.PokedexAdapter;
import com.daviprojetos.pokemon.api.PokemonService;
import com.daviprojetos.pokemon.model.Pokemon;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

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
    private PokemonService service;
    private Retrofit retrofit;
    private String urlApi;
    private ImageLoader imageLoader;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recuperandoDados();
        //Configurando o ImageLoader para fazer o download das imagens
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.vazia)
                .showImageOnFail(R.drawable.erro)
                .showImageOnLoading(R.drawable.carregando)
                .cacheInMemory(true)
                .displayer(new FadeInBitmapDisplayer(1000))
                .build();


        ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(MainActivity.this)
                .defaultDisplayImageOptions(displayImageOptions)
                .memoryCacheSize(50*1024*1024)
                .threadPoolSize(5)
                .build();

        imageLoader = ImageLoader.getInstance();
        imageLoader.init(conf);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Informações do GridView
                GridView gridPokedex = findViewById(R.id.gridPokedex);

                //Configurar Adapter
                PokedexAdapter adapter = new PokedexAdapter(listaPokemons,getApplicationContext(),imageLoader);
                gridPokedex.setAdapter(adapter);

                gridPokedex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getBaseContext(), "Imagem: "+position, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        },5000);


    }

    @Override
    protected void onStart() {
        super.onStart();
        //recuperarListaPokemon();
        //recuperandoDados();
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

            JSONArray objetoPokemon = null;


            try {
                JSONObject jsonObject = new JSONObject(s);
                objetoPokemon = jsonObject.getJSONArray("pokemon");

                for(int i =0;i<objetoPokemon.length();i++){
                    List<String>tipos=new ArrayList<>();
                    List<String>fraquezas=new ArrayList<>();
                    tipos.clear();
                    fraquezas.clear();
                    JSONObject jsonObjectDadosPokemon = new JSONObject(objetoPokemon.getString(i));
                    Pokemon pokemon = new Pokemon();
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
                    //Adicionando o Pokemon a lista Principal
                    listaPokemons.add(pokemon);


                    /*System.out.println("ID POKEMON: "+pokemon.getId() +" Número Pokemon: "+ pokemon.getNum()+ " Nome: "+pokemon.getName()
                    +" Endereço da imagem: "+pokemon.getImg()+" Tamanho: "+pokemon.getHeight()+" Peso: "+pokemon.getWeight()+"    TIPOS: "+pokemon.getType()+"   FRAQUEZAS: "+ pokemon.getWeaknesses());*/
                }
/*
                for(int i = 0 ; i<listaPokemons.size();i++){
                    Pokemon poke = listaPokemons.get(i);
                    System.out.println("Lista FASES: "+poke.getName());
                }*/


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}