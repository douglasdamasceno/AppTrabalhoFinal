package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.apptrabalhofinal.R;

public class CriarEnderecoAtividadeActivity extends AppCompatActivity {

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

        endAtividadeCEP.setText("122222");
    }
}
