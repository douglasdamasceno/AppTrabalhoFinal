package com.example.apptrabalhofinal.present;

import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.data.model.Endereco;
import com.example.apptrabalhofinal.present.interfaces.ContratoCriarEnderecoAtividade;

public class PresentCriarEnderecoAtividade implements ContratoCriarEnderecoAtividade.present {
    private AtividadeDAO atividadeDAO = AtividadeFirebaseDAO.getInstance();//AtividadeDBMemoriaDAO.getInstance();

    private Atividade atividadeArmazenar;

    private ContratoCriarEnderecoAtividade.view view;

    public PresentCriarEnderecoAtividade(ContratoCriarEnderecoAtividade.view view){
        this.view = view;
    }

    @Override
    public void receberAtividade(String email,String nome,String descricao,String tipo, String qtd, String idade,String sexo,String hora,String data){
        atividadeArmazenar = new Atividade(nome,descricao,tipo,Integer.parseInt(qtd),idade,sexo,hora,data);
        atividadeArmazenar.setEmailProprietario(email);
    }

    @Override
    public boolean validarEnderecoAtividade(String cidade, String rua, String estado, String cep, String complemento) {
        if(!cidade.isEmpty() && !rua.isEmpty() && !estado.isEmpty() && !cep.isEmpty() && !complemento.isEmpty()){
            view.criarAtividade();

            Endereco endereco = new Endereco(cidade,rua,estado,cep,complemento);
            atividadeArmazenar.setEndereco(endereco);
            return true;
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
    public void salvarAtividade() {
        atividadeDAO.addNovo(atividadeArmazenar);

    }

    @Override
    public void editarAtividade(String id,String cidade, String rua, String estado, String cep, String complemento) {
        Atividade atividade = atividadeDAO.AtividadePorID(id);
        Endereco endereco = new Endereco(cep,rua,complemento,cidade,estado);
        atividade.setEndereco(endereco);
        atividadeDAO.editar(id,atividade);
    }
}
