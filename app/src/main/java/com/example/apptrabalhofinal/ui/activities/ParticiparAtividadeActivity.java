package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeDBMemoriaDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDBMemoriaDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.data.model.Participante;
import com.example.apptrabalhofinal.data.model.Usuario;

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
    Usuario usuarioAutentificado;
    Atividade atividadeParticipar;
    AtividadeDAO atividadeDAO = AtividadeDBMemoriaDAO.getInstance();
    UsuarioDAO usuarioDAO = UsuarioDBMemoriaDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participar_atividade);

        referenciarElementos();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            atividadeParticipar = atividadeDAO.AtividadePorID(bundle.getString("id"));
            usuarioAutentificado = usuarioDAO.getUsuarioPorEmail(bundle.getString("email"));
        }
        referenciarValores();

        btnParticipar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Participante participante = new Participante();
                if(usuarioAutentificado!=null) {
                    participante.setId(usuarioAutentificado.getId());
                    participante.setEmail(usuarioAutentificado.getMeuPerfil().getEmail());
                    participante.setNome(usuarioAutentificado.getMeuPerfil().getNome());
                }
                atividadeParticipar.addParticipante(participante);
                //atualizar os participantes
                atividadeDAO.editar(atividadeParticipar.getId(),atividadeParticipar);
                Toast.makeText(ParticiparAtividadeActivity.this,"Participando atividade ok",Toast.LENGTH_LONG).show();
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
        }
    }
    //click
    public void participarAtividade(View view){
        Participante participante = new Participante();
        participante.setId(usuarioAutentificado.getId());
        participante.setEmail(usuarioAutentificado.getMeuPerfil().getEmail());
        participante.setNome(usuarioAutentificado.getMeuPerfil().getNome());
        atividadeParticipar.addParticipante(participante);
        //atualizar os participantes
        atividadeDAO.editar(atividadeParticipar.getId(),atividadeParticipar);

        Toast.makeText(this,"Participando atividade",Toast.LENGTH_LONG).show();
    }

}
