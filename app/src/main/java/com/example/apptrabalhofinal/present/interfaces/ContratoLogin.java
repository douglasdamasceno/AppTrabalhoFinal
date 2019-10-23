package com.example.apptrabalhofinal.present.interfaces;

public interface ContratoLogin {
    public interface view{
        void formatoInvalidoEmail();
        void senhaInvalida();
        void realizarlogin(String email);
        void usuarioComEmailInvalido();
        void usuarioComSenha();
    }

    public interface present{
       void validarLogin(String email,String senha);
    }
}
