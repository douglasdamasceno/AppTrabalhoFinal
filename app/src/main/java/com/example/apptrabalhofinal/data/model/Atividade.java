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
    private String hora;
    private String data;

    private ArrayList<Participante> meusParticipantes;

    public Atividade(){
        endereco = new Endereco();
        meusParticipantes = new ArrayList<Participante>();
    }


    public Atividade( String nome, String descricao, String tipoDeAtividade,
                     int vagasParticipantes,  String idadePublico, String sexoPublico,String hora,String data) {
        this.nome = nome;
        this.descricao = descricao;
        this.tipoDeAtividade = tipoDeAtividade;
        this.vagasParticipantes = vagasParticipantes;
        this.idadePublico = idadePublico;
        this.sexoPublico = sexoPublico;
        this.hora =hora;
        this.data = data;
        endereco = new Endereco();
        meusParticipantes = new ArrayList<Participante>();
    }



    public void addParticipante(Participante participante){
        if(meusParticipantes.size()<= vagasParticipantes) {
            meusParticipantes.add(participante);
        }
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

    public String getIdadePublico() {
        return idadePublico;
    }

    public void setIdadePublico(String idadePublico) {
        this.idadePublico = idadePublico;
    }

    public String getSexoPublico() {
        return sexoPublico;
    }

    public void setSexoPublico(String sexoPublico) {
        this.sexoPublico = sexoPublico;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Atividade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", tipoDeAtividade='" + tipoDeAtividade + '\'' +
                ", vagasParticipantes=" + vagasParticipantes +
                ", endereco=" + endereco +
                ", idadePublico='" + idadePublico + '\'' +
                ", sexoPublico='" + sexoPublico + '\'' +
                ", hora='" + hora + '\'' +
                ", data='" + data + '\'' +
                ", meusParticipantes=" + meusParticipantes +
                '}';
    }
}
