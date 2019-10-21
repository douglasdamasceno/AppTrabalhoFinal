package com.example.apptrabalhofinal.present;

import android.util.Patterns;

import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDBMemoriaDAO;
import com.example.apptrabalhofinal.present.interfaces.Login;


public class PresentLogin  implements  Login.present{
    Login.view loginActivity;
    UsuarioDAO usuarioDAO = UsuarioDBMemoriaDAO.getInstance();

    public PresentLogin(Login.view loginActivity){
        this.loginActivity = loginActivity;
    }

    @Override
    public void validarLogin(String email, String senha) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginActivity.formatoInvalidoEmail();
        }
        if(senha.length()<6){
            loginActivity.senhaInvalida();
        }
        if(usuarioDAO.getLogin(email,senha)){
            loginActivity.realizarlogin();
        }else if(usuarioDAO.getEmailUsuario(email)){
            loginActivity.usuarioComSenha();
        }else{
            loginActivity.usuarioComEmailInvalido();
        }

    }
}