package com.example.apptrabalhofinal.present.interfaces;

import com.example.apptrabalhofinal.data.model.Atividade;

public interface ContratoCriarEnderecoAtividade {
    interface view{
        public void cidadeInvalido() ;
        public void ruaInvalido() ;
        public void estadoInvalido() ;
        public void cepInvalido() ;
        public void complementoInvalido() ;

        //esse metedo vai e no cadastro deve ter um toast informa o sucesso
        public void criarAtividade() ;

    }
    interface present{
        boolean validarEnderecoAtividade(String cidade,String rua,String estado,String cep,String complemento);
        void criarAtividade(Atividade atividade);
    }
}
