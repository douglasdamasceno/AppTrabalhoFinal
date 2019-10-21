package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.present.interfaces.CriarAtividade;

public class CriarEnderecoAtividadeActivity extends AppCompatActivity  {

    private EditText endAtividadeCidade;
    private EditText endAtividadeRua;
    private EditText endAtividadeEstado;
    private EditText endAtividadeCEP;
    private EditText endAtividadeComplemento;
    private Button btnCriarAtividade;

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
    }

    public void criarAtividade(View view){
        Bundle bundle = getIntent().getExtras();

        String nome = bundle.getString("nome");
        String descricao = bundle.getString("descricao");
        String tipo = bundle.getString("tipo");
        String qtd = bundle.getString("qtd");
        String idade = bundle.getString("idade");
        String sexo = bundle.getString("sexo");
        String hora = bundle.getString("hora");
        String data = bundle.getString("data");

        Atividade atividade = new Atividade(nome,descricao,tipo, Integer.parseInt(qtd),idade,sexo);
        Toast.makeText(this,atividade.toString(),Toast.LENGTH_LONG).show();
    }

}
