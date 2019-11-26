package com.example.apptrabalhofinal.data.dao;

import android.net.Uri;

import com.example.apptrabalhofinal.data.model.Usuario;
import com.google.firebase.auth.FirebaseUser;


public interface UsuarioDAO {
    void addNovo(String fotoPerfil, String username, String email, String senha);
    void editar(String id,String email,String username,String senha,String idade,String sexo);
    void atualizarID(String id);
    void remover(int id);
    boolean getLogin(String email,String senha);
    boolean getEmailUsuario(String email);
    Usuario getUsuarioPorEmail(String email);
    Usuario getUserPorID(String id);

    FirebaseUser getFirebaseUser();
}
