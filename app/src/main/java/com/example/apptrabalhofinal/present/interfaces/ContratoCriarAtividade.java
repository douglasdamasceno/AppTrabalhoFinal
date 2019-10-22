package com.example.apptrabalhofinal.present.interfaces;

public interface ContratoCriarAtividade {
    public interface view{
        public void nomeAtividadeInvalido() ;
        public void descricaoAtividadeInvalido() ;
        public void quantidadeAtividadeInvalido() ;
        public void tipoAtividadeInvalido() ;
        public void dataAtividadeInvalido() ;
        public void horarioAtividadeInvalido() ;
        public void idadePublicAtividadeInvalido() ;
        public void sexoPublicAtividadeInvalido() ;

        public void atividadeValida();
    }

    public interface present{
        boolean validarAtividade(String nome,String descricao,String quantidade,String tipo,
                                 String data,String horario,String idade,String sexo);
        }
}
