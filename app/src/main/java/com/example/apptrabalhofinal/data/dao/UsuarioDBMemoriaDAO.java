package com.example.apptrabalhofinal.data.dao;

import com.example.apptrabalhofinal.data.model.Usuario;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class UsuarioDBMemoriaDAO implements  UsuarioDAO {

    private static int proximoIdUser = 0;

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
        proximoIdUser++;
        Usuario usuario = new Usuario();
        usuario.setId(proximoIdUser+"");
        usuario.getMeuPerfil().setNome(username);
        usuario.getMeuPerfil().setEmail(email);
        usuario.getMeuPerfil().setSenha(senha);
        listaDeUsuarios.add(usuario);
    }

    @Override
    public void editar(String email, String username, String senha, String idade, String sexo) {
        for (Usuario usuario: listaDeUsuarios) {
            if(email.equals(usuario.getMeuPerfil().getIdPerfil())){
                usuario.getMeuPerfil().setNome(username);
                usuario.getMeuPerfil().setSenha(senha);
                usuario.getMeuPerfil().setIdade(idade);
                usuario.getMeuPerfil().setSexo(sexo);
            }
        }
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

    @Override
    public Usuario getUsuarioPorEmail(String email) {
        for (Usuario usuario: listaDeUsuarios) {
            if(email.equals(usuario.getMeuPerfil().getEmail())){
                return usuario;
            }
        }
        return null;
    }


    @Override
    public String getIDporEmail(String id) {
        for (Usuario usuario: listaDeUsuarios) {
            if(id.equals(usuario.getMeuPerfil().getIdPerfil())){
                return id;
            }
        }
        return null;
    }

    @Override
    public Usuario getUserPorID(String id) {
        for (Usuario usuario: listaDeUsuarios) {
            if(id.equals(usuario.getMeuPerfil().getIdPerfil())){
                return usuario;
            }
        }
        return  null;
    }

    @Override
    public FirebaseUser getUsuarioAutentificado() {
        return null;
    }

}
