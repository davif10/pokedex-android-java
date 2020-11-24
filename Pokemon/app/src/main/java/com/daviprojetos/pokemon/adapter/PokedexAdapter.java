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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PokedexAdapter extends BaseAdapter {
    private List<Pokemon> listaPokemon;
    private Context context;
    private Pokemon pokemon;
    private ImageLoader imageLoader;
    private Bitmap img = null;

    public PokedexAdapter(List<Pokemon> listaPokemon, Context c,ImageLoader imgLoader) {
        this.listaPokemon = listaPokemon;
        this.context = c;
        this.imageLoader = imgLoader;
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
        textNumero.setText("Nº"+pokemon.getNum());

        //Lista com os Tipos e adicionando cor de fundo no botão
        List<String>tipos=new ArrayList<>();
        tipos = pokemon.getType();
        textTipo1.setText(tipos.get(0));
        String corFundoBotao = tipos.get(0);
        switch (corFundoBotao){
            case "Grass":textTipo1.setBackgroundResource(R.color.Grass);
                break;
            case "Poison":textTipo1.setBackgroundResource(R.color.Poison);
                break;
            case "Fire":textTipo1.setBackgroundResource(R.color.Fire);
                break;
            case "Flying":textTipo1.setBackgroundResource(R.color.Flying);
                break;
            case "Ice":textTipo1.setBackgroundResource(R.color.Ice);
                break;
            case "Psychic":textTipo1.setBackgroundResource(R.color.Psychic);
                break;
            case "Water":textTipo1.setBackgroundResource(R.color.Water);
                break;
            case "Ground":textTipo1.setBackgroundResource(R.color.Ground);
                break;
            case "Rock":textTipo1.setBackgroundResource(R.color.Rock);
                break;
            case "Electric":textTipo1.setBackgroundResource(R.color.Electric);
                break;
            case "Bug":textTipo1.setBackgroundResource(R.color.Bug);
                break;
            case "Normal":textTipo1.setBackgroundResource(R.color.Normal);
                break;
            case "Fighting":textTipo1.setBackgroundResource(R.color.Fighting);
                break;
            case "Ghost":textTipo1.setBackgroundResource(R.color.Ghost);
                break;
            case "Dark":textTipo1.setBackgroundResource(R.color.Dark);
                break;
            case "Fairy":textTipo1.setBackgroundResource(R.color.Fairy);
                break;
            default:textTipo1.setBackgroundResource(R.color.black);
                break;
        }

        if(tipos.size() == 2){
            textTipo2.setText(tipos.get(1));
            String corFundoBotao2 =tipos.get(1);

            //Adicionando cor ao fundo
            switch (corFundoBotao2){
                case "Grass":textTipo2.setBackgroundResource(R.color.Grass);
                    break;
                case "Poison":textTipo2.setBackgroundResource(R.color.Poison);
                    break;
                case "Fire":textTipo2.setBackgroundResource(R.color.Fire);
                    break;
                case "Flying":textTipo2.setBackgroundResource(R.color.Flying);
                    break;
                case "Ice":textTipo2.setBackgroundResource(R.color.Ice);
                    break;
                case "Psychic":textTipo2.setBackgroundResource(R.color.Psychic);
                    break;
                case "Water":textTipo2.setBackgroundResource(R.color.Water);
                    break;
                case "Ground":textTipo2.setBackgroundResource(R.color.Ground);
                    break;
                case "Rock":textTipo2.setBackgroundResource(R.color.Rock);
                    break;
                case "Electric":textTipo2.setBackgroundResource(R.color.Electric);
                    break;
                case "Bug":textTipo2.setBackgroundResource(R.color.Bug);
                    break;
                case "Normal":textTipo2.setBackgroundResource(R.color.Normal);
                    break;
                case "Fighting":textTipo2.setBackgroundResource(R.color.Fighting);
                    break;
                case "Ghost":textTipo2.setBackgroundResource(R.color.Ghost);
                    break;
                case "Dark":textTipo2.setBackgroundResource(R.color.Dark);
                    break;
                case "Fairy":textTipo2.setBackgroundResource(R.color.Fairy);
                    break;
                default:textTipo2.setBackgroundResource(R.color.black);
                    break;
            }


        }else{
            textTipo2.setVisibility(View.GONE);
        }

        //Configurando a imagem
        String urlAlterada = pokemon.getImg().replace("http","https");
        //System.out.println(urlAlterada);

        imageLoader.displayImage(urlAlterada, imagemPoke, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                System.out.println("onLoadingStarted iniciou");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                System.out.println("onLoadingFailed iniciou");
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                System.out.println("onLoadingComplete iniciou");
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                System.out.println("onLoadingCancelled iniciou");
            }

        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                System.out.println("ononProgressUpdate iniciou\n IMAGEURI: "+imageUri+" VIEW: "+view+" CURRENT:"+current+" TOTAL: "+total);
            }
        });



        return convertView;
    }

}
