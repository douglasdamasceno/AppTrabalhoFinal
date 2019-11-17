package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
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

    AtividadeDAO atividadeDAO = AtividadeFirebaseDAO.getInstance();//AtividadeDBMemoriaDAO.getInstance();
    private Toolbar myToolbar;
    private ListView listViewMinhasAtividades;

    private String emailProprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_atividade_lista);

        inicializarElementos();

        listViewMinhasAtividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abriItem(i);
            }
        });
    }


    public void abriItem(int itemSelecionado) {
        Bundle bundle = getIntent().getExtras();

        if(bundle!=null) {
            emailProprietario = bundle.getString("email");
        }
        if (listarTodasAtividades(emailProprietario).size() > 0) {
            Intent intent = new Intent(this, ParticiparAtividadeActivity.class);
            Atividade atividade = listarTodasAtividades(emailProprietario).get(itemSelecionado);
            intent.putExtra("id", atividade.getId());
            intent.putExtra("email", emailProprietario);
            intent.putExtra("chamada", "buscar");
            startActivity(intent);
        }
    }

    public ArrayList<Atividade> listarTodasAtividades(String email){
        return atividadeDAO.listarAtividadesTodos(email);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            emailProprietario = bundle.getString("email");
        }
        listViewMinhasAtividades.setAdapter(new MinhaAtividadeAdapter(this,listarTodasAtividades(emailProprietario)));
    }

    void inicializarElementos(){
        myToolbar = findViewById(R.id.minhaToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Busca");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listViewMinhasAtividades = findViewById(R.id.lista_view_todas_atividades);
    }
}
