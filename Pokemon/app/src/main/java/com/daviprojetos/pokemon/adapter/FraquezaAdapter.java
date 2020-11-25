package com.daviprojetos.pokemon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.daviprojetos.pokemon.R;
import com.daviprojetos.pokemon.model.Pokemon;

import java.util.List;

public class FraquezaAdapter extends BaseAdapter {

    private List<String> listaFraqueza;
    private Context context;

    public FraquezaAdapter(List<String> listaFraqueza, Context context) {
        this.listaFraqueza = listaFraqueza;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaFraqueza.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFraqueza.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.adapter_fraqueza,parent,false);
        }

        //Vincular dados
        TextView textFraqueza = convertView.findViewById(R.id.textFraqueza);
        String fraqueza = listaFraqueza.get(position);

        //Adicionando cor ao fundo
        switch (fraqueza){
            case "Grass":textFraqueza.setBackgroundResource(R.color.Grass);
                break;
            case "Poison":textFraqueza.setBackgroundResource(R.color.Poison);
                break;
            case "Fire":textFraqueza.setBackgroundResource(R.color.Fire);
                break;
            case "Flying":textFraqueza.setBackgroundResource(R.color.Flying);
                break;
            case "Ice":textFraqueza.setBackgroundResource(R.color.Ice);
                break;
            case "Psychic":textFraqueza.setBackgroundResource(R.color.Psychic);
                break;
            case "Water":textFraqueza.setBackgroundResource(R.color.Water);
                break;
            case "Ground":textFraqueza.setBackgroundResource(R.color.Ground);
                break;
            case "Rock":textFraqueza.setBackgroundResource(R.color.Rock);
                break;
            case "Electric":textFraqueza.setBackgroundResource(R.color.Electric);
                break;
            case "Bug":textFraqueza.setBackgroundResource(R.color.Bug);
                break;
            case "Normal":textFraqueza.setBackgroundResource(R.color.Normal);
                break;
            case "Fighting":textFraqueza.setBackgroundResource(R.color.Fighting);
                break;
            case "Ghost":textFraqueza.setBackgroundResource(R.color.Ghost);
                break;
            case "Dark":textFraqueza.setBackgroundResource(R.color.Dark);
                break;
            case "Fairy":textFraqueza.setBackgroundResource(R.color.Fairy);
                break;
            case "Dragon":textFraqueza.setBackgroundResource(R.color.Dragon);
                break;
            case "Steel":textFraqueza.setBackgroundResource(R.color.Steel);
                break;
            default:textFraqueza.setBackgroundResource(R.color.black);
                break;
        }


        return convertView;
    }
}
