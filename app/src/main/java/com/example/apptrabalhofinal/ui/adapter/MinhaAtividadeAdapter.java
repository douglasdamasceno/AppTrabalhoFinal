package com.example.apptrabalhofinal.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.model.Atividade;

import java.util.ArrayList;

public class MinhaAtividadeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Atividade> minhasAtividades;

    public MinhaAtividadeAdapter(Context context, ArrayList<Atividade> minhasAtividades) {
        this.context = context;
        this.minhasAtividades = minhasAtividades;
    }

    @Override
    public int getCount() {
        return minhasAtividades.size();
    }

    @Override
    public Atividade getItem(int i) {
        return minhasAtividades.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if( view == null){
            view = LayoutInflater.from(context).inflate(R.layout.lista_minhas_atividades,viewGroup , false);
        }
        Atividade atividade = getItem(i);

        TextView viewNome = view.findViewById(R.id.lista_minha_atividades_nome);
        TextView viewDescricao = view.findViewById(R.id.lista_minha_atividades_descricao);
        ImageView viewImage = view.findViewById(R.id.lista_minha_atividades_imagem);


        viewNome.setText(atividade.getNome());
        viewDescricao.setText(atividade.getDescricao());

        return view;
    }
}
