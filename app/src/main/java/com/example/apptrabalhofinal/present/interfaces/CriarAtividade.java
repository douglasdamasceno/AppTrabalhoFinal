package com.example.apptrabalhofinal.present.interfaces;

public interface CriarAtividade {
    public interface view{
        public void nomeAtividadeInvalido() ;
        public void descricaoAtividadeInvalido() ;
        public void quantidadeAtividadeInvalido() ;
        public void tipoAtividadeInvalido() ;
        public void dataAtividadeInvalido() ;
        public void HorarioAtividadeInvalido() ;
        public void idadePublicAtividadeInvalido() ;
        public void sexoPublicAtividadeInvalido() ;
    }

    public interface present{
        boolean validarAtividade();
        boolean validarEnderecoAtividade();
        void criarAtividade();
    }
}
