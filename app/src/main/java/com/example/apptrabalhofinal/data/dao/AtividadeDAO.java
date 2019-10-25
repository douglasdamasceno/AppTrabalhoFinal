package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Atividade;

import java.util.ArrayList;

public interface AtividadeDAO {
    ArrayList<Atividade> listarAtividadesTodos(String email);
    ArrayList<Atividade> listarMinhasAtividades(String email);
    Atividade AtividadePorID(String id);
    void addNovo(Atividade atividade);
    void editar(String id,Atividade atividade);
    void remover(int id);
    void getAtividade(int id);
}
