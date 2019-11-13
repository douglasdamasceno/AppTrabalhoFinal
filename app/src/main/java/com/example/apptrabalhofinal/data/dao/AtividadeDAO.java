package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Atividade;

import java.util.ArrayList;

public interface AtividadeDAO {
    ArrayList<Atividade> listarAtividadesTodos(String email);
    ArrayList<Atividade> listarMinhasAtividades(String email);
    ArrayList<Atividade> listarAtividadesParticipante(String email);
    Atividade AtividadePorID(String id);
    void addNovo(Atividade atividade);
    void editar(String id,Atividade atividade);
    boolean remover(String id);
    void getAtividade(String id);
}
