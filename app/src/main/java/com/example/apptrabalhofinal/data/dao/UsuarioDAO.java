package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Usuario;
import com.google.firebase.auth.FirebaseUser;


public interface UsuarioDAO {
    void addNovo(String username, String email,String senha);
    void editar(String email,String username,String senha,String idade,String sexo);
    void remover(int id);
    void getUsuario(int id);
    boolean getLogin(String email,String senha);
    boolean getEmailUsuario(String email);
    Usuario getUsuarioPorEmail(String email);
    String getIDporEmail(String email);
    Usuario getUserPorID(String id);

    FirebaseUser getUsuarioAutentificado();
}
