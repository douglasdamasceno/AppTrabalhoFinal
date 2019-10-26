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
    public ArrayList<Atividade> listarAtividadesTodos(String email) {
        ArrayList<Atividade> listaDeNovasAtividades = new ArrayList<Atividade>();
        for (Atividade atividade: listaAtividades) {
            if(!email.equals(atividade.getEmailProprietario()) && !atividade.buscarParticipante(email)){
                listaDeNovasAtividades.add(atividade);
            }
        }
        return listaDeNovasAtividades;
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
    public ArrayList<Atividade> listarAtividadesParticipante(String email) {
        ArrayList<Atividade> listaAtividadeParticipo = new ArrayList<Atividade>();
        for (Atividade atividade: listaAtividades) {
            if(atividade.buscarParticipante(email)){
                listaAtividadeParticipo.add(atividade);
            }
        }
        return listaAtividadeParticipo;
    }

    @Override
    public Atividade AtividadePorID(String id) {
        for (Atividade atividade: listaAtividades) {
            if(id.equals(atividade.getId())){
                return atividade;
            }
        }
        return null;
    }

    @Override
    public void addNovo(Atividade atividade) {
        proximoIdAtividade++;
        atividade.setId(proximoIdAtividade+"");
        listaAtividades.add(atividade);
    }

    @Override
    public void editar(String id, Atividade atividade) {
        for (Atividade a: listaAtividades) {
            if(id.equals(a.getId())){
                a.setNome(atividade.getNome());
                a.setDescricao(atividade.getDescricao());
                a.setVagasParticipantes( atividade.getVagasParticipantes());
                a.setIdadePublico(atividade.getIdadePublico());
                a.setSexoPublico(atividade.getSexoPublico());
                a.setData(atividade.getData());
                a.setHora(atividade.getHora());
                a.setTipoDeAtividade(atividade.getTipoDeAtividade());
                a.setMeusParticipantes(atividade.getMeusParticipantes());
                a.setEndereco(atividade.getEndereco());
            }
        }
    }

    @Override
    public void remover(int id) {
        for (Atividade atividade: listaAtividades) {
            if(""+id==atividade.getId()){

            }
        }
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
