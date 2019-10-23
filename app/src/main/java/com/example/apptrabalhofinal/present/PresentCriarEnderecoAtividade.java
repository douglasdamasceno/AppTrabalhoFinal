package com.example.apptrabalhofinal.present;

import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeDBMemoriaDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.data.model.Endereco;
import com.example.apptrabalhofinal.present.interfaces.ContratoCriarEnderecoAtividade;

public class PresentCriarEnderecoAtividade implements ContratoCriarEnderecoAtividade.present {
    private AtividadeDAO atividadeDAO = AtividadeDBMemoriaDAO.getInstance();
    Atividade atividadeArmazenar;
    private ContratoCriarEnderecoAtividade.view view;

    public PresentCriarEnderecoAtividade(ContratoCriarEnderecoAtividade.view view){
        this.view = view;
    }
//erro devido ao numero grande limitar o campo para apenas 4 digito 9999
    @Override
    public void receberAtividade(String nome,String descricao,String tipo, String qtd, String idade,String sexo,String hora,String data){
        atividadeArmazenar = new Atividade(nome,descricao,tipo,Integer.parseInt( qtd),idade,sexo,hora,data);
    }

    @Override
    public boolean validarEnderecoAtividade(String cidade, String rua, String estado, String cep, String complemento) {
        if(!cidade.isEmpty() && !rua.isEmpty() && !estado.isEmpty() && !cep.isEmpty() && !complemento.isEmpty()){
            view.criarAtividade();

            Endereco endereco = new Endereco(cidade,rua,estado,cep,complemento);
            atividadeArmazenar.setEndereco(endereco);
            this.salvarAtividade(atividadeArmazenar);

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
    public void salvarAtividade(Atividade atividade) {
        atividadeDAO.addNovo(atividade);
    }
}
