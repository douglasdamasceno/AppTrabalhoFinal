package com.example.apptrabalhofinal.data.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario  implements Serializable {
    private String id;
    private ArrayList<Atividade> minhaAtividades;

    PerfilContaUsuario meuPerfil;

    public Usuario(){
        minhaAtividades = new ArrayList<Atividade>();
        meuPerfil = new PerfilContaUsuario();
        meuPerfil.setIdPerfil(this.id);
    }

    public Usuario(String id,String fotoPerfil,String nome,String email,String senha){
        this.id = id;

        minhaAtividades = new ArrayList<Atividade>();

        meuPerfil = new PerfilContaUsuario();

        meuPerfil.setIdPerfil(this.id);
        meuPerfil.setNome(nome);
        meuPerfil.setEmail(email);
        meuPerfil.setSenha(senha);
        meuPerfil.setFotoPerfil(fotoPerfil);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Atividade> getMinhaAtividades() {
        return minhaAtividades;
    }

    public void setMinhaAtividades(ArrayList<Atividade> minhaAtividades) {
        this.minhaAtividades = minhaAtividades;
    }

    public PerfilContaUsuario getMeuPerfil() {
        return meuPerfil;
    }

    public void setMeuPerfil(PerfilContaUsuario meuPerfil) {
        this.meuPerfil = meuPerfil;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", minhaAtividades=" + minhaAtividades +
                ", meuPerfil=" + meuPerfil +
                '}';
    }
}
