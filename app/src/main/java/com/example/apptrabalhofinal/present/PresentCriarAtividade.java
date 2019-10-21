package com.example.apptrabalhofinal.present;

import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeDBMemoriaDAO;
import com.example.apptrabalhofinal.present.interfaces.CriarAtividade;

public class PresentCriarAtividade implements CriarAtividade.present {
    private CriarAtividade.view view;
    AtividadeDAO atividadeDAO = AtividadeDBMemoriaDAO.getInstance();

    public PresentCriarAtividade(CriarAtividade.view view){
        this.view =view;
    }

    @Override
    public boolean validarAtividade() {



        return false;
    }

    @Override
    public boolean validarEnderecoAtividade() {
        return false;
    }

    @Override
    public void criarAtividade() {

    }
}
