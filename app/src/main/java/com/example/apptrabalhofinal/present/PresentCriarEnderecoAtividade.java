package com.example.apptrabalhofinal.present;

import android.widget.Toast;

import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeDBMemoriaDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.data.model.Endereco;
import com.example.apptrabalhofinal.present.interfaces.ContratoCriarEnderecoAtividade;

public class PresentCriarEnderecoAtividade implements ContratoCriarEnderecoAtividade.present {
    private AtividadeDAO atividadeDAO = AtividadeDBMemoriaDAO.getInstance();

    private ContratoCriarEnderecoAtividade.view view;

    public PresentCriarEnderecoAtividade(ContratoCriarEnderecoAtividade.view view){
        this.view = view;
    }

    @Override
    public boolean validarEnderecoAtividade(String cidade, String rua, String estado, String cep, String complemento) {
        if(!cidade.isEmpty() && !rua.isEmpty() && !estado.isEmpty() && !cep.isEmpty() && !complemento.isEmpty()){
            //chamo meu criar e o da view
            view.criarAtividade();
            Endereco endereco;
            // criarAtividade(atividade);
        }else{
            if(cidade.isEmpty()){
                view.cidadeInvalido();
            }
            if(rua.isEmpty()){
                view.ruaInvalido();
            }
            if(estado.isEmpty()){
                view.estadoInvalido();
            }
            if(cep.isEmpty()){
               view.cepInvalido();
            }
            if(complemento.isEmpty()){
                view.complementoInvalido();
            }
        }

        return false;
    }

    @Override
    public void criarAtividade(Atividade atividade) {
        atividadeDAO.addNovo(atividade);
    }
}
