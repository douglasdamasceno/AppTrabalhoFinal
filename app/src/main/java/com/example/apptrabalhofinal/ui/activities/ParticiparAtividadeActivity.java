package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.apptrabalhofinal.R;

public class ParticiparAtividadeActivity extends AppCompatActivity {
    TextView atividadeNome;
    TextView atividadeDescricao;
    TextView atividadeData;
    TextView atividadeHorario;
    TextView atividadeTipo;
    TextView atividadeQuantidades;
    TextView atividadeIdadePublicoAlvo;
    TextView atividadeSexoPublicoAlvo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participar_atividade);

        referenciarElementos();

    }

    void referenciarElementos(){
        atividadeNome = findViewById(R.id.participar_atividade_nome);
        atividadeDescricao = findViewById(R.id.participar_atividade_descricao);
        atividadeData = findViewById(R.id.participar_atividade_data);
        atividadeHorario= findViewById(R.id.participar_atividade_horario);
        atividadeTipo = findViewById(R.id.participar_atividade_tipo);
        atividadeQuantidades = findViewById(R.id.participar_atividade_quatidade);
        atividadeIdadePublicoAlvo = findViewById(R.id.participar_atividade_idade);
        atividadeSexoPublicoAlvo = findViewById(R.id.participar_atividade_sexo);
    }


}
