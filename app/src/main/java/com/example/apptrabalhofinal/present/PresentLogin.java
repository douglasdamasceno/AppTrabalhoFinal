package com.example.apptrabalhofinal.present;

import android.util.Log;
import android.util.Patterns;

import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDBMemoriaDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioFirebaseDAO;
import com.example.apptrabalhofinal.present.interfaces.ContratoLogin;


public class PresentLogin  implements  ContratoLogin.present{
    ContratoLogin.view loginActivity;
    UsuarioDAO usuarioFirebaseDAO = UsuarioFirebaseDAO.getInstance();

    //UsuarioDAO usuarioDAO = UsuarioDBMemoriaDAO.getInstance();

    public PresentLogin(ContratoLogin.view loginActivity){
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
        //if(usuarioDAO.getLogin(email,senha)){
        if(usuarioFirebaseDAO.getLogin(email,senha)==true){
            Log.i("teste","Retorno do login: true");
            loginActivity.realizarlogin(email);
        }else if(usuarioFirebaseDAO.getEmailUsuario(email)){
            loginActivity.usuarioComSenha();
        }else{
            loginActivity.usuarioComEmailInvalido();
        }

    }
}
