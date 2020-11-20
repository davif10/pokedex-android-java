package com.daviprojetos.pokemon.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daviprojetos.pokemon.R;
import com.daviprojetos.pokemon.model.Pokemon;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PokedexAdapter extends BaseAdapter {
    private List<Pokemon> listaPokemon;
    private Context context;
    private Pokemon pokemon;
    Handler handler = new Handler();
    Thread thread = new Thread();
    Bitmap img = null;

    public PokedexAdapter(List<Pokemon> listaPokemon, Context c) {
        this.listaPokemon = listaPokemon;
        this.context = c;
    }



    @Override
    public int getCount() {
        return listaPokemon.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPokemon.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView  = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_pokedex,parent,false);

        }


        //Vinculando os dados
        TextView textNome = convertView.findViewById(R.id.textNomePokemon);
        TextView textNumero = convertView.findViewById(R.id.textNumPokemon);
        TextView textTipo1 = convertView.findViewById(R.id.textTipo1);
        TextView textTipo2 = convertView.findViewById(R.id.textTipo2);
        ImageView imagemPoke = convertView.findViewById(R.id.imagePokemon);


        pokemon = listaPokemon.get(position);
        textNome.setText(pokemon.getName());
        textNumero.setText(pokemon.getNum());
        textTipo1.setText(pokemon.getType().toString());
        textTipo2.setText("Tipo2");
        //Configurando a imagem
            String urlAlterada = pokemon.getImg().replace("http","https");
        System.out.println(urlAlterada);
        new Thread(){
            @Override
            public void run() {

                super.run();
                try{

                    URL url = new URL(urlAlterada);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = conexao.getInputStream();
                    img = BitmapFactory.decodeStream(inputStream);

                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                final Bitmap imgAux = img;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imagemPoke.setImageBitmap(imgAux);
                        imagemPoke.setAdjustViewBounds(true);
                    }
                });

            }
        };




        return convertView;
    }

    private void criarConexaoImagem(){

        new Thread(){
            @Override
            public void run() {
                super.run();
                try{
                    Bitmap img ;
                    URL url = new URL(pokemon.getImg());
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = conexao.getInputStream();
                    img = BitmapFactory.decodeStream(inputStream);

                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        };

    }
}
