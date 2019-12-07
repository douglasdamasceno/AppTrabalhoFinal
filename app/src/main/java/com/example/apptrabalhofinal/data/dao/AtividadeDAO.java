package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Atividade;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public interface AtividadeDAO {
    ArrayList<Atividade> listarAtividadesTodos(String email);
    ArrayList<Atividade> listarMinhasAtividades(String email);
    ArrayList<Atividade> listarAtividadesParticipante(String idUsuario);
    Atividade AtividadePorID(String id);
    void addNovo(Atividade atividade);
    void editar(String id,Atividade atividade);
    void remover(String id);
    Atividade getAtividade(String id);
    void participarAtividade(String idUsuario,String email,String idAtividade);
    void naoParticiparAtividade(String idUsuario,String idAtividade);
    FirebaseFirestore getDatabase();
}
