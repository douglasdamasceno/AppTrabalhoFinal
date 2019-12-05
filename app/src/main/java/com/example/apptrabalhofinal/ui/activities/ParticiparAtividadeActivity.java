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
    Atividade atividadeParticipar;
    AtividadeDAO atividadeDAO = AtividadeFirebaseDAO.getInstance();//AtividadeDBMemoriaDAO.getInstance();
    UsuarioDAO usuarioDAO = UsuarioFirebaseDAO.getInstance();//UsuarioDBMemoriaDAO.getInstance();
    FirebaseUser userFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participar_atividade);

        referenciarElementos();
        userFirebase = usuarioDAO.getFirebaseUser();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            atividadeParticipar = atividadeDAO.AtividadePorID(bundle.getString("id"));
        }
        referenciarValores();

        btnParticipar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                if(bundle!=null) {
                    if (bundle.getString("chamada").equals("buscar")) {
                        atividadeDAO.participarAtividade(userFirebase.getUid(),userFirebase.getEmail()
                                ,atividadeParticipar.getId());
                    } else {
                        atividadeDAO.naoParticiparAtividade(userFirebase.getUid(),atividadeParticipar.getId());
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
    void referenciarValores(){
        if(atividadeParticipar!=null) {
            String nome = atividadeParticipar.getNome();
            String descricao = atividadeParticipar.getDescricao();
            int quatidade = atividadeParticipar.getVagasParticipantes();
            String idade = atividadeParticipar.getIdadePublico();
            String sexo = atividadeParticipar.getSexoPublico();
            String data = atividadeParticipar.getData();
            String horario = atividadeParticipar.getHora();
            String tipo = atividadeParticipar.getTipoDeAtividade();

            atividadeNome.setText("Nome: "+nome);
            atividadeDescricao.setText("Descrição: \n"+descricao);
            atividadeData.setText("Data: "+data);
            atividadeHorario.setText("Horario: "+horario);
            atividadeTipo.setText("Tipo: "+tipo);
            atividadeQuantidades.setText("Participantes:"+ atividadeParticipar.getMeusParticipantes().size()+"/"+quatidade);
            atividadeIdadePublicoAlvo.setText("Publico Alvo: "+idade);
            atividadeSexoPublicoAlvo.setText("Genero: "+sexo);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        referenciarValores();
    }

    @Override
    protected void onResume() {
        super.onResume();
        referenciarValores();
    }
}
