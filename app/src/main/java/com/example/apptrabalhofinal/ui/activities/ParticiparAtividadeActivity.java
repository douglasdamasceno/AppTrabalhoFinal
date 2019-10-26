package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import com.example.apptrabalhofinal.ui.adapter.MinhaAtividadeAdapter;

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
                Bundle bundle = getIntent().getExtras();
                if(bundle!=null) {
                    if (bundle.getString("chamada").equals("buscar")) {
                        atividadeParticipar.addParticipante(participante);
                    } else {
                        atividadeParticipar.removePorIdParticipante(participante.getId());

                    }
                }
                atividadeDAO.editar(atividadeParticipar.getId(),atividadeParticipar);
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
    protected void onResume() {
        super.onResume();
        referenciarValores();
    }
}
