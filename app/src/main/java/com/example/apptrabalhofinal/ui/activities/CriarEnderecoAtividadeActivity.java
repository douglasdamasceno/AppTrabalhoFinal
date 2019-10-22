package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.data.model.Endereco;
import com.example.apptrabalhofinal.present.PresentCriarEnderecoAtividade;
import com.example.apptrabalhofinal.present.interfaces.ContratoCriarEnderecoAtividade;

public class CriarEnderecoAtividadeActivity extends AppCompatActivity  implements ContratoCriarEnderecoAtividade.view {

    private EditText endAtividadeCidade;
    private EditText endAtividadeRua;
    private EditText endAtividadeEstado;
    private EditText endAtividadeCEP;
    private EditText endAtividadeComplemento;
    private Button btnCriarAtividade;

    ContratoCriarEnderecoAtividade.present present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_endereco_atividade);

        inicializarElementos();
    }


    void inicializarElementos(){
        endAtividadeCidade = findViewById(R.id.end_id_cidade);
        endAtividadeRua = findViewById(R.id.end_id_rua);
        endAtividadeEstado = findViewById(R.id.end_id_estado);
        endAtividadeCEP = findViewById(R.id.end_id_cep);
        endAtividadeComplemento = findViewById(R.id.end_id_complemento);
        btnCriarAtividade = findViewById(R.id.btn_criar_atividade);
        present = new PresentCriarEnderecoAtividade(this);
    }

    public void validarAtividade(View view){
        Bundle bundle = getIntent().getExtras();
        String nome = bundle.getString("nome");
        String descricao = bundle.getString("descricao");
        String tipo = bundle.getString("tipo");
        String qtd = bundle.getString("qtd");
        String idade = bundle.getString("idade");
        String sexo = bundle.getString("sexo");
        String hora = bundle.getString("hora");
        String data = bundle.getString("data");


        present.receberAtividade(nome,descricao,tipo,qtd,idade,sexo,hora,data);
        present.validarEnderecoAtividade(
                endAtividadeCidade.getText().toString(),
                endAtividadeRua.getText().toString(),
                endAtividadeEstado.getText().toString(),
                endAtividadeCEP.getText().toString(),
                endAtividadeComplemento.getText().toString()
        );
    }

    @Override
    public void cidadeInvalido() {
        endAtividadeCidade.setFocusable(true);
        endAtividadeCidade.setError("cidade invalido");
    }

    @Override
    public void ruaInvalido() {
        endAtividadeRua.setFocusable(true);
        endAtividadeRua.setError("rua invalido");
    }

    @Override
    public void estadoInvalido() {
        endAtividadeEstado.setFocusable(true);
        endAtividadeEstado.setError("estado invalido");
    }

    @Override
    public void cepInvalido() {
        endAtividadeCEP.setFocusable(true);
        endAtividadeCEP.setError("cep invalido");
    }

    @Override
    public void complementoInvalido() {
        endAtividadeComplemento.setFocusable(true);
        endAtividadeComplemento.setError("complemento invalido");
    }

    @Override
    public void criarAtividade() {
        Toast.makeText(this,"Atividade criada",Toast.LENGTH_LONG).show();
    }
}
