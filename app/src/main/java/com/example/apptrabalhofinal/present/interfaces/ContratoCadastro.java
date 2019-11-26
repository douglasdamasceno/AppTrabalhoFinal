package com.example.apptrabalhofinal.present.interfaces;

import android.net.Uri;

public interface ContratoCadastro {
    public interface view{
        void formatoUsernameInvalido();
        void formatoInvalidoEmail();
        void senhaInvalida();
        void realizarCadastro();


    }

    public interface present{
        void validarCadastro(String fotoPerfil,String username, String email, String senha);
    }
}
