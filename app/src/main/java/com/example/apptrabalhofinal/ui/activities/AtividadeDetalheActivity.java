package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Atividade;

public class AtividadeDetalheActivity extends AppCompatActivity {

    AtividadeDAO atividadeDAO = AtividadeFirebaseDAO.getInstance();

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
        if(bundle!=null) {
            atividadeEditada = atividadeDAO.getAtividade(bundle.getString("id"));
        }
        referenciarElementos();
        atualizarValoresAtividade();


        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtendoValores();
                finish();
            }
        });
//ediarebdereco ajeita
        btnEndereco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(atividadeEditada!=null) {
                    obtendoValores();
                    editarEndereco();
                }
            }
        });
    }
    public void obtendoValores(){
        Atividade atividade = new Atividade();
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        atividade.setId(id);

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

    public void atualizarValoresAtividade(){
        Bundle bundle = getIntent().getExtras();

        String nome = bundle.getString("nome");
        String descricao = bundle.getString("descricao");
        String quantidade = bundle.getString("quantidade");
        String data = bundle.getString("data");
        String hora = bundle.getString("hora");
        String tipo = bundle.getString("tipo");
        String idade = bundle.getString("idade");
        String sexo = bundle.getString("sexo");

        atividadeNome.setText(nome);
        atividadeDescricao.setText(descricao);
        atividadeQuantidade.setText(quantidade);
        atividadeIdade.setText(idade);
        atividadeSexo.setText(sexo);
        atividadeData.setText(data);
        atividadeHorario.setText(hora);
        tipoAtividade.setText(tipo);
    }

    public void editarEndereco(){
        Bundle bundle = getIntent().getExtras();

        Intent intent = new Intent(this, AtividadeDetalheEnderecoActivity.class);

        intent.putExtra("chamada","editar");
        intent.putExtra("email", bundle.getString("email"));
        intent.putExtra("id",bundle.getString("id"));

        intent.putExtra("nome", atividadeNome.getText().toString());
        intent.putExtra("descricao", atividadeDescricao.getText().toString());
        intent.putExtra("quantidade", atividadeQuantidade.getText().toString());
        intent.putExtra("data", atividadeData.getText().toString());
        intent.putExtra("hora", atividadeHorario.getText().toString());
        intent.putExtra("tipo", tipoAtividade.getText().toString());
        intent.putExtra("idade", atividadeIdade.getText().toString());
        intent.putExtra("sexo", atividadeSexo.getText().toString());

        intent.putExtra("cidade",bundle.getString("cidade"));
        intent.putExtra("rua",bundle.getString("rua"));
        intent.putExtra("estado",bundle.getString("estado"));
        intent.putExtra("cep",bundle.getString("cep"));
        intent.putExtra("complemento",bundle.getString("complemento"));

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

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
