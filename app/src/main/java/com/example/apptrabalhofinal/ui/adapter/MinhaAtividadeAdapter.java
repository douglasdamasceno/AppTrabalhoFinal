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
        if(minhasAtividades!=null) {
            return minhasAtividades.size();
        }
        return -1;
    }

    @Override
    public Atividade getItem(int i) {
        return minhasAtividades.get(i);
    }
    //nao usar esse metodo
    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if( view == null){
            view = LayoutInflater.from(context).inflate(R.layout.lista_minhas_atividades,viewGroup , false);
        }
        Atividade atividade = getItem(i);

        TextView viewNome = view.findViewById(R.id.lista_minha_atividades_nome);
        TextView viewDescricao = view.findViewById(R.id.lista_minha_atividades_descricao);
        TextView viewData = view.findViewById(R.id.lista_minha_atividades_data);
        TextView viewHorario = view.findViewById(R.id.lista_minha_atividades_horario);

        //ImageView viewImage = view.findViewById(R.id.lista_minha_atividades_imagem);


        viewNome.setText(atividade.getNome());
        viewDescricao.setText(atividade.getDescricao());
        viewData.setText(atividade.getData());
        viewHorario.setText(atividade.getHora());

        return view;
    }
}
