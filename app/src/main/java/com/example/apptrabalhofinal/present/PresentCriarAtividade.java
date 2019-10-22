package com.example.apptrabalhofinal.present;

import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeDBMemoriaDAO;
import com.example.apptrabalhofinal.present.interfaces.ContratoCriarAtividade;

public class PresentCriarAtividade implements ContratoCriarAtividade.present {
    private ContratoCriarAtividade.view view;
    //a view ce criar atividade, esta pasado a atividade para a proxima tela
    public PresentCriarAtividade(ContratoCriarAtividade.view view){
        this.view =view;
    }


    @Override
    public boolean validarAtividade(String nome, String descricao, String quantidade,
                                    String tipo, String data, String horario, String idade, String sexo) {
        if( (!nome.isEmpty() || nome.length()>=5) && (!descricao.isEmpty() || descricao.length()>=10) &&
         (!data.isEmpty() ) && (!horario.isEmpty()) &&  (!idade.isEmpty() ) && (!sexo.isEmpty())){
            view.atividadeValida();
            return  true;
        }else{
            if(nome.isEmpty() || nome.length()<5){
                view.nomeAtividadeInvalido();
            }
            if(descricao.isEmpty() || descricao.length()<10){
                view.descricaoAtividadeInvalido();
            }
            if(quantidade.isEmpty()){
                view.quantidadeAtividadeInvalido();
            }
            if(data.isEmpty()){
                view.dataAtividadeInvalido();
            }
            if(horario.isEmpty()){
                view.horarioAtividadeInvalido();
            }
            if(idade.isEmpty()){
                view.idadePublicAtividadeInvalido();
            }
            if (sexo.isEmpty()){
                view.sexoPublicAtividadeInvalido();
            }
        }
        return false;
    }

 }
