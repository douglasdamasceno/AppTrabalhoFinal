package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Atividade;

import java.util.ArrayList;

public class AtividadeDBMemoriaDAO implements AtividadeDAO {
    private ArrayList<Atividade> listaAtividades;
    private static AtividadeDBMemoriaDAO minhaInstancia;

    private AtividadeDBMemoriaDAO(){
        listaAtividades = new ArrayList<Atividade>();
    }

    public static AtividadeDBMemoriaDAO getInstance(){
        if(minhaInstancia==null){
            minhaInstancia = new AtividadeDBMemoriaDAO();
        }
        return minhaInstancia;
    }

    @Override
    public ArrayList<Atividade> listarAtividadesTodos() {
        return listaAtividades;
    }

    @Override
    public void addNovo(Atividade atividade) {
        listaAtividades.add(atividade);
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

    public ArrayList<Atividade> getListaAtividades() {
        return listaAtividades;
    }

    public void setListaAtividades(ArrayList<Atividade> listaAtividades) {
        this.listaAtividades = listaAtividades;
    }
}