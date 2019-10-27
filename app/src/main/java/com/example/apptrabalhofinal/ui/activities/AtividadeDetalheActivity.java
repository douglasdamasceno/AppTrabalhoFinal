package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeDBMemoriaDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDBMemoriaDAO;
import com.example.apptrabalhofinal.data.model.Atividade;

public class AtividadeDetalheActivity extends AppCompatActivity {

    AtividadeDAO atividadeDAO = AtividadeDBMemoriaDAO.getInstance();


    private String emailProprietario;

    private TextView atividadeNome;
    private TextView atividadeDescricao;
    private TextView atividadeQuantidade;
    private TextView atividadeIdade;
    private TextView atividadeSexo;

    private TextView  atividadeData;
    private TextView  atividadeHorario;
    private TextView tipoAtividade;

    Atividade atividadeEditada;
    private Button btnSalva;
    private Button btnEndereco;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_detalhe);

        Bundle bundle = getIntent().getExtras();
        atividadeEditada = atividadeDAO.AtividadePorID(bundle.getString("id"));
        emailProprietario= bundle.getString("email");


        referenciarElementos();
        atualizarValores();

        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtendoValores(atividadeEditada);
                finish();
            }
        });

        btnEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtendoValores(atividadeEditada);
                editarEndereco(atividadeEditada);
            }
        });
    }

    public void obtendoValores(Atividade atividade){
        atividade.setNome(atividadeNome.getText().toString());
        atividade.setDescricao(atividadeDescricao.getText().toString());
        atividade.setVagasParticipantes( Integer.parseInt(atividadeQuantidade.getText().toString()));
        atividade.setIdadePublico(atividadeIdade.getText().toString());
        atividade.setSexoPublico(atividadeSexo.getText().toString());
        atividade.setData(atividadeData.getText().toString());
        atividade.setHora(atividadeHorario.getText().toString());
        atividade.setTipoDeAtividade(tipoAtividade.getText().toString());
        atividadeDAO.editar(atividade.getId(),atividade);
    }

    public void editarEndereco(Atividade atividade){
        Intent intent = new Intent(this, AtividadeDetalheEnderecoActivity.class);
        intent.putExtra("chamada","editar");
        intent.putExtra("email", emailProprietario);
        intent.putExtra("id",atividade.getId());
        intent.putExtra("cidade",atividade.getEndereco().getCidade());
        intent.putExtra("rua",atividade.getEndereco().getRua());
        intent.putExtra("estado",atividade.getEndereco().getEstado());
        intent.putExtra("cep",atividade.getEndereco().getCep());
        intent.putExtra("complemento",atividade.getEndereco().getComplemento());
        startActivity(intent);
        finish();
    }

    private void referenciarElementos(){
        atividadeNome = findViewById(R.id.criar_atividade_nome);
        atividadeDescricao = findViewById(R.id.criar_atividade_descricao);
        atividadeQuantidade = findViewById(R.id.criar_atividade_quatidade);
        atividadeIdade = findViewById(R.id.criar_atividade_idade);
        atividadeSexo = findViewById(R.id.criar_atividade_sexo);
        atividadeData = findViewById(R.id.criar_atividade_data);
        atividadeHorario = findViewById(R.id.criar_atividade_horario);
        tipoAtividade =  findViewById(R.id.criar_atividade_tipo);
        btnSalva = findViewById(R.id.btn_editar_atividade);
        btnEndereco = findViewById(R.id.btn_editar_endereco);
    }

    void atualizarValores(){
        String nome = atividadeEditada.getNome();
        String descricao = atividadeEditada.getDescricao();
        int quatidade = atividadeEditada.getVagasParticipantes();
        String idade = atividadeEditada.getIdadePublico();
        String sexo = atividadeEditada.getSexoPublico();
        String data = atividadeEditada.getData();
        String horario = atividadeEditada.getHora();
        String tipo = atividadeEditada.getTipoDeAtividade();

        atividadeNome.setText(nome);
        atividadeDescricao.setText(descricao);
        atividadeQuantidade.setText(""+quatidade);
        atividadeIdade.setText(idade);
        atividadeSexo.setText(sexo);
        atividadeData.setText(data);
        atividadeHorario.setText(horario);
        tipoAtividade.setText(tipo);
    }

}
