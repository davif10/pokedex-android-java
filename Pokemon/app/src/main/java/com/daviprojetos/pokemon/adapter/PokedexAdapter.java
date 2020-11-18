package com.daviprojetos.pokemon.adapter;

import android.content.Context;
import android.graphics.Color;
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

import java.util.List;

public class PokedexAdapter extends BaseAdapter {
    List<Pokemon> listaPokemon;
    Context context;

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

        if(convertView== null){
            convertView  = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_pokedex,parent,false);
        }

        //Vinculando os dados
        TextView textNome = convertView.findViewById(R.id.textNomePokemon);
        TextView textNumero = convertView.findViewById(R.id.textNumPokemon);
        TextView textTipo1 = convertView.findViewById(R.id.textTipo1);
        TextView textTipo2 = convertView.findViewById(R.id.textTipo2);
        ImageView imagemPoke = convertView.findViewById(R.id.imagePokemon);


        textNome.setText("Nome do Pokemon");
        textNumero.setText("Número do Pokemon");
        textTipo1.setText("Tipo 1");
        textTipo2.setText("Tipo2");

        //imagemPoke.setImageResource(R.);



        /*Button button;
        if(convertView == null){
            button = new Button(context);
            button.setLayoutParams(new ViewGroup.LayoutParams(85,85));
            button.setText(listaPokemon.get(position));
            button.setBackgroundColor(Color.DKGRAY);
            button.setTextColor(Color.YELLOW);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, button.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            button =(Button) convertView;

        }*/
        return convertView;
    }
}
