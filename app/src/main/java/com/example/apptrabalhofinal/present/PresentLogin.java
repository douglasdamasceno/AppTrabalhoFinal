package com.example.apptrabalhofinal.present;

import android.util.Patterns;

import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDBMemoriaDAO;
import com.example.apptrabalhofinal.present.interfaces.Login;


public class PresentLogin  implements  Login.present{
    Login.view loginActivity;
    UsuarioDAO usuarioDAO = UsuarioDBMemoriaDAO.getInstance();
    private String emailMock = "douglas@gmail.com";
    private String senhaMock = "123456";

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
        }

        if(!email.equals(emailMock)){
            //  email nao cadastrada email mock sera do dao
            loginActivity.usuarioComEmailInvalido();
        }
        if(!email.equals(emailMock)){
            //senha não cadastrada
            loginActivity.usuarioComSenha();
        }
    }
}
