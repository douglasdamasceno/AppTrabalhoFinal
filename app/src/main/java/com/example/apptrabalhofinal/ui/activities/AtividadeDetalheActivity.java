package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.example.apptrabalhofinal.R;

public class AtividadeDetalheActivity extends AppCompatActivity {

    EditText nomeAtividade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_detalhe);

        nomeAtividade = findViewById(R.id.id_detalhe_nome);

        String nome = getIntent().getExtras().getString("nome");

        nomeAtividade.setText(nome);

    }
}
