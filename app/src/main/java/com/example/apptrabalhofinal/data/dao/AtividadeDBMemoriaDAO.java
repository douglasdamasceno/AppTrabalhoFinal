package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Atividade;

import java.util.ArrayList;

public class AtividadeDBMemoriaDAO implements AtividadeDAO {
    private static int proximoIdAtividade = 0;
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
    public ArrayList<Atividade> listarMinhasAtividades(String email) {
        ArrayList<Atividade> listaProprietario = new ArrayList<Atividade>();
        for (Atividade atividade: listaAtividades) {
            if(email.equals(atividade.getEmailProprietario())){
                listaProprietario.add(atividade);
            }
        }
        return listaProprietario;
    }

    @Override
    public void addNovo(Atividade atividade) {
        proximoIdAtividade++;
        atividade.setId(proximoIdAtividade+"");
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
