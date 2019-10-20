package com.example.apptrabalhofinal.data.model;

import java.util.ArrayList;

public class Atividade {
    private int id;
    private String nome;
    private String descricao;
    private String tipoDeAtividade;
    private int vagasParticipantes;
    private Endereco endereco;
    private String idadePublico;
    private String sexoPublico;
    private ArrayList<Participante> meusParticipantes;

    public Atividade(){
        endereco = new Endereco();
        meusParticipantes = new ArrayList<Participante>();
    }

    public void addParticipante(Participante participante){
        meusParticipantes.add(participante);
    }
    public void removerParticipante(Participante participante){
        meusParticipantes.remove(participante);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoDeAtividade() {
        return tipoDeAtividade;
    }

    public void setTipoDeAtividade(String tipoDeAtividade) {
        this.tipoDeAtividade = tipoDeAtividade;
    }

    public int getVagasParticipantes() {
        return vagasParticipantes;
    }

    public void setVagasParticipantes(int vagasParticipantes) {
        this.vagasParticipantes = vagasParticipantes;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

}
