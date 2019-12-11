package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.ui.adapter.MinhaAtividadeAdapter;

import java.util.ArrayList;

public class BuscarAtividadeListaActivity extends AppCompatActivity {

    AtividadeDAO atividadeDAO = AtividadeFirebaseDAO.getInstance();
    private Toolbar myToolbar;
    private ListView listViewMinhasAtividades;
    MinhaAtividadeAdapter minhaAtividadeAdapter;
    private String emailProprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_atividade_lista);

        inicializarElementos();
        criarListaTodasAtividades();

        listViewMinhasAtividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abriItem(i);
            }
        });
    }


    public void abriItem(int itemSelecionado) {
        if (listarTodasAtividades(emailProprietario).size() > 0) {
            Intent intent = new Intent(this, ParticiparAtividadeActivity.class);
            Atividade atividade = listarTodasAtividades(emailProprietario).get(itemSelecionado);
            intent.putExtra("id", atividade.getId());
            intent.putExtra("email", atividade.getEmailProprietario());
            intent.putExtra("chamada", "buscar");
            intent.putExtra("nome", atividade.getNome());
            intent.putExtra("descricao", atividade.getDescricao());
            intent.putExtra("quantidade", Integer.toString(atividade.getVagasParticipantes()));
            intent.putExtra("data", atividade.getData());
            intent.putExtra("hora", atividade.getHora());
            intent.putExtra("tipo", atividade.getTipoDeAtividade());
            intent.putExtra("idade", atividade.getIdadePublico());
            intent.putExtra("sexo", atividade.getSexoPublico());
            intent.putExtra("totalParticantes", Integer.toString(atividade.getMeusParticipantes().size()));

            startActivity(intent);
        }
    }

    public ArrayList<Atividade> listarTodasAtividades(String email){
        return atividadeDAO.listarAtividadesTodos(email);
    }

    @Override
    protected void onResume() {
        super.onResume();
        notificarMeuAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        notificarMeuAdapter();
    }

    void criarListaTodasAtividades(){
        Bundle bundle = getIntent().getExtras();
        emailProprietario = bundle.getString("email");
        minhaAtividadeAdapter = new MinhaAtividadeAdapter(this,listarTodasAtividades(emailProprietario));
        listViewMinhasAtividades.setAdapter(minhaAtividadeAdapter);
    }

    void notificarMeuAdapter(){
        minhaAtividadeAdapter.notifyDataSetChanged();}

    void inicializarElementos(){
        myToolbar = findViewById(R.id.minhaToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Listar Atividades");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listViewMinhasAtividades = findViewById(R.id.lista_view_todas_atividades);
    }
}
