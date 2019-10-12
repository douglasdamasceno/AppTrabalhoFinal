package com.example.apptrabalhofinal.present;

import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.apptrabalhofinal.present.interfaces.Cadastro;

public class PresentCadastro implements Cadastro.present {
    private Cadastro.view cadastroActivity;

    public PresentCadastro(Cadastro.view cadastroActivity){
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
            Log.i("err","dddddd");
            //salva no dao tbm
            cadastroActivity.realizarCadastro();
        }
    }
}
