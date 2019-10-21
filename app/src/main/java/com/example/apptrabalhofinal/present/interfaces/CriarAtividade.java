package com.example.apptrabalhofinal.present.interfaces;

public interface CriarAtividade {
    public interface view{
    }
    public interface present{
        boolean validarAtividade();
        boolean validarEnderecoAtividade();
        void criarAtividade();
    }
}
