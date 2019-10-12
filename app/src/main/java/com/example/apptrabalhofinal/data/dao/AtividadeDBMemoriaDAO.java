package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Atividade;

import java.util.ArrayList;

public class AtividadeDBMemoriaDAO implements AtividadeDAO {
    ArrayList<Atividade> listaAtividades;

    private static AtividadeDBMemoriaDAO minhaInstancia;

    private AtividadeDBMemoriaDAO(){
        listaAtividades = new ArrayList<Atividade>();
    }

    public AtividadeDBMemoriaDAO getInstance(){
        if(minhaInstancia==null){
            minhaInstancia = new AtividadeDBMemoriaDAO();
        }
        return minhaInstancia;
    }

    @Override
    public ArrayList<Atividade> listarAtividadesTodos() {
        return null;
    }

    @Override
    public void addNovo(Atividade atividade) {

    }

    @Override
    public void editar(Atividade atividade) {

    }

    @Override
    public void remover(int id) {

    }

    @Override
    public void getAtividade(int id) {

    }
}
