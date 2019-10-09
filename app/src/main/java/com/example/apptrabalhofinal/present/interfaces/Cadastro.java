package com.example.apptrabalhofinal.present.interfaces;

public interface Cadastro {
    public interface view{
        void formatoUsernameInvalido();
        void formatoInvalidoEmail();
        void senhaInvalida();
        void realizarCadastro();
    }

    public interface present{
        void validarCadastro(String username,String email,String senha);
    }
}
