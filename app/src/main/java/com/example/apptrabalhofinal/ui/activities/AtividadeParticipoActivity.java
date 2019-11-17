package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.ui.adapter.MinhaAtividadeAdapter;

import java.util.ArrayList;

public class AtividadeParticipoActivity extends AppCompatActivity {

    AtividadeDAO atividadeDAO = AtividadeFirebaseDAO.getInstance();//AtividadeDBMemoriaDAO.getInstance();
    private Toolbar myToolbar;
    private ListView listViewMinhasAtividades;

    private String emailProprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_participo);

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
        if (listarAtividadesParticipo(emailProprietario).size() > 0) {
            Intent intent = new Intent(this, ParticiparAtividadeActivity.class);
            Atividade atividade = listarAtividadesParticipo(emailProprietario).get(itemSelecionado);
            intent.putExtra("id", atividade.getId());
            intent.putExtra("email", emailProprietario);
            intent.putExtra("chamada", "participo");
            startActivity(intent);
        }
    }

    public ArrayList<Atividade> listarAtividadesParticipo(String email){
        return atividadeDAO.listarAtividadesParticipante(email);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            emailProprietario = bundle.getString("email");
        }
        listViewMinhasAtividades.setAdapter
                (new MinhaAtividadeAdapter(this,listarAtividadesParticipo(emailProprietario)));
        Toast.makeText(this,"tamanho: "+listarAtividadesParticipo(emailProprietario).size(),Toast.LENGTH_LONG).show();
    }
    void inicializarElementos(){
        myToolbar = findViewById(R.id.minhaToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Participo");
        listViewMinhasAtividades = findViewById(R.id.lista_view_atividades_participo);

    }
}
