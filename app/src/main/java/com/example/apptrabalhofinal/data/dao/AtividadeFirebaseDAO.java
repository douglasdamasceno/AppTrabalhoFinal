package com.example.apptrabalhofinal.data.dao;


import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.apptrabalhofinal.data.model.Atividade;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AtividadeFirebaseDAO  implements  AtividadeDAO{

    AtividadeFirebaseDAO(){
       // db = FirebaseFirestore.getInstance();
    }

    @Override
    public ArrayList<Atividade> listarAtividadesTodos(String email) {

        return null;
    }

    @Override
    public ArrayList<Atividade> listarMinhasAtividades(String email) {
        return null;
    }

    @Override
    public ArrayList<Atividade> listarAtividadesParticipante(String email) {
        return null;
    }

    @Override
    public Atividade AtividadePorID(String id) {
        return null;
    }

    @Override
    public void addNovo(Atividade atividade) {

    }

    @Override
    public void editar(String id, Atividade atividade) {}

    @Override
    public boolean remover(String id) {

        return false;
    }

    @Override
    public void getAtividade(int id) {}
}
