package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Atividade;

import java.util.ArrayList;

public interface AtividadeDAO {
    ArrayList<Atividade> listarAtividadesTodos();
    void addNovo(Atividade atividade);
    void editar(Atividade atividade);
    void remover(int id);
    void getAtividade(int id);
}
