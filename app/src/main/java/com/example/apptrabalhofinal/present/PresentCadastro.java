package com.example.apptrabalhofinal.present;

import android.util.Log;
import android.util.Patterns;

import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioFirebaseDAO;
import com.example.apptrabalhofinal.present.interfaces.ContratoCadastro;

public class PresentCadastro implements ContratoCadastro.present {
    private ContratoCadastro.view cadastroActivity;
    //UsuarioDAO usuarioDAO = UsuarioDBMemoriaDAO.getInstance();
    UsuarioDAO usuarioDAOFirebase = UsuarioFirebaseDAO.getInstance();

    public PresentCadastro(ContratoCadastro.view cadastroActivity){
        this.cadastroActivity = cadastroActivity;
    }

    @Override
    public void validarCadastro(String username, String email, String senha) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            cadastroActivity.formatoInvalidoEmail();
        }
        if(senha.length()<6){
            cadastroActivity.senhaInvalida();
        }
        if(username.length()<6){
            cadastroActivity.formatoUsernameInvalido();
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches() && senha.length()>=6 && username.length()>=6){
           // usuarioDAO.addNovo(username,email,senha);
            usuarioDAOFirebase.addNovo(username,email,senha);

            cadastroActivity.realizarCadastro();
        }
    }
}
