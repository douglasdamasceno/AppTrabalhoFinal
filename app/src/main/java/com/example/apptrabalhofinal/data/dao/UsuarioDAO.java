package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Usuario;


public interface UsuarioDAO {
    void addNovo(String username, String email,String senha);
    void editar(Usuario usuario);
    void remover(int id);
    void getUsuario(int id);
    boolean getLogin(String email,String senha);
}
