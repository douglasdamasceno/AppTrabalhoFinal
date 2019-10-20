package com.example.apptrabalhofinal.data.model;

import java.util.ArrayList;

public class Usuario {
    private String id;
    private ArrayList<Atividade> minhaAtividades;
    PerfilContaUsuario meuPerfil;

    public Usuario(){
        minhaAtividades = new ArrayList<Atividade>();
        meuPerfil = new PerfilContaUsuario();
    }


}
