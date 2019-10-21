package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Usuario;

import java.util.ArrayList;

public class UsuarioDBMemoriaDAO implements  UsuarioDAO {
    public static UsuarioDBMemoriaDAO usuarioDBMemoriaDAO;
    private ArrayList<Usuario> listaDeUsuarios;

    private UsuarioDBMemoriaDAO(){
        listaDeUsuarios = new ArrayList<Usuario>();
    }
    public static UsuarioDBMemoriaDAO getInstance(){
        if(usuarioDBMemoriaDAO==null){
            usuarioDBMemoriaDAO = new UsuarioDBMemoriaDAO();
        }
        return  usuarioDBMemoriaDAO;
    }


    @Override
    public void addNovo(String username, String email, String senha) {
        Usuario usuario = new Usuario();
        usuario.getMeuPerfil().setNome(username);
        usuario.getMeuPerfil().setEmail(email);
        usuario.getMeuPerfil().setSenha(senha);
        listaDeUsuarios.add(usuario);
    }

    @Override
    public void editar(Usuario usuario) {

    }

    @Override
    public void remover(int id) {

    }

    @Override
    public void getUsuario(int id) {

    }

    @Override
    public boolean getLogin(String email, String senha) {
        for (Usuario usuario:listaDeUsuarios) {
            if(usuario.getMeuPerfil().getEmail().equals(email) && usuario.getMeuPerfil().getSenha().equals(senha)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean getEmailUsuario(String email) {
        for (Usuario usuario: listaDeUsuarios) {
            if(email.equals(usuario.getMeuPerfil().getEmail())){
                return true;
            }
        }
        return false;
    }
}
