package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.data.model.Endereco;

public class AtividadeDetalheEnderecoActivity extends AppCompatActivity {

    private EditText endAtividadeCidade;
    private EditText endAtividadeRua;
    private EditText endAtividadeEstado;
    private EditText endAtividadeCEP;
    private EditText endAtividadeComplemento;
    private Button btnEditarAtividade;

    AtividadeDAO atividadeDAO = AtividadeFirebaseDAO.getInstance();//AtividadeDBMemoriaDAO.getInstance();
    //Present present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_detalhe_endereco);

        inicializarElementos();
        atualizarCamposEndereco();

        btnEditarAtividade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvaEdicao();
            }
        });
    }

    void inicializarElementos(){
        endAtividadeCidade = findViewById(R.id.end_id_cidade);
        endAtividadeRua = findViewById(R.id.end_id_rua);
        endAtividadeEstado = findViewById(R.id.end_id_estado);
        endAtividadeCEP = findViewById(R.id.end_id_cep);
        endAtividadeComplemento = findViewById(R.id.end_id_complemento);
        btnEditarAtividade = findViewById(R.id.btn_criar_atividade);
       // present = new PresentCriarEnderecoAtividade(this);
    }

    void atualizarCamposEndereco() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("chamada").equals("editar")) {
            btnEditarAtividade.setText("Editar");
            String id = bundle.getString("id");
            String cidade = bundle.getString("cidade");
            String rua = bundle.getString("rua");
            String estado = bundle.getString("estado");
            String cep = bundle.getString("cep");
            String complemento = bundle.getString("complemento");

            endAtividadeCidade.setText(cidade);
            endAtividadeRua.setText(rua);
            endAtividadeEstado.setText(estado);
            endAtividadeCEP.setText(cep);
            endAtividadeComplemento.setText(complemento);
        }
    }

    public void salvaEdicao() {
        Bundle bundle = getIntent().getExtras();
        if (bundle.getString("chamada").equals("editar")) {
            btnEditarAtividade.setText("Editar");

            String id = bundle.getString("id");

            Atividade atividade = atividadeDAO.AtividadePorID(id);
            Endereco endereco = new Endereco();
            endereco.setCidade(endAtividadeCidade.getText().toString());
            endereco.setRua(endAtividadeRua.getText().toString());
            endereco.setEstado(endAtividadeEstado.getText().toString());
            endereco.setCep(endAtividadeCEP.getText().toString());
            endereco.setComplemento(endAtividadeComplemento.getText().toString());
            atividade.setEndereco(endereco);
            atividadeDAO.editar(id,atividade);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarCamposEndereco();
    }
}
