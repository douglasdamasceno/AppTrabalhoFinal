package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeFirebaseDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.data.model.Participante;
import com.example.apptrabalhofinal.data.model.Usuario;
import com.google.firebase.auth.FirebaseUser;

public class ParticiparAtividadeActivity extends AppCompatActivity {
    TextView atividadeNome;
    TextView atividadeDescricao;
    TextView atividadeData;
    TextView atividadeHorario;
    TextView atividadeTipo;
    TextView atividadeQuantidades;
    TextView atividadeIdadePublicoAlvo;
    TextView atividadeSexoPublicoAlvo;

    Button btnParticipar;
    //Atividade atividadeParticipar;
    AtividadeDAO atividadeDAO = AtividadeFirebaseDAO.getInstance();
    UsuarioDAO usuarioDAO = UsuarioFirebaseDAO.getInstance();
    FirebaseUser userFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participar_atividade);

        referenciarElementos();
        userFirebase = usuarioDAO.getFirebaseUser();

        referenciarValoresAtividade();

        btnParticipar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                if(bundle!=null) {
                    String idAtividade = bundle.getString("id");
                    if (bundle.getString("chamada").equals("buscar")) {

                        atividadeDAO.participarAtividade(userFirebase.getUid(),userFirebase.getEmail()
                               ,idAtividade);
                    } else {
                        atividadeDAO.naoParticiparAtividade(userFirebase.getUid(),idAtividade);
                    }
                }
                finish();
            }
        });
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
        btnParticipar = findViewById(R.id.btn_participar_atividade);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if(!bundle.getString("chamada").equals("buscar")){
                btnParticipar.setText("Não Participar");
            }else{
                btnParticipar.setText("Participar");
            }
        }
    }

    void referenciarValoresAtividade(){
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            String nome = bundle.getString("nome");
            String descricao = bundle.getString("descricao");
            String quantidade = bundle.getString("quantidade");
            String idade = bundle.getString("idade");
            String sexo = bundle.getString("sexo");
            String data = bundle.getString("data");
            String horario = bundle.getString("hora");
            String tipo = bundle.getString("tipo");
            String totalParticipantes = bundle.getString("totalParticantes");

            atividadeNome.setText("Nome: " + nome);
            atividadeDescricao.setText("Descrição: \n" + descricao);
            atividadeData.setText("Data: " + data);
            atividadeHorario.setText("Horario: " + horario);
            atividadeTipo.setText("Tipo: " + tipo);
            atividadeQuantidades.setText("Participantes:" + totalParticipantes + "/" + quantidade);
            atividadeIdadePublicoAlvo.setText("Publico Alvo: " + idade);
            atividadeSexoPublicoAlvo.setText("Genero: " + sexo);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
