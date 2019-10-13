package com.example.apptrabalhofinal.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeDBMemoriaDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.ui.adapter.MinhaAtividadeAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements BottomNavigationView.OnNavigationItemSelectedListener{

    private ListView listViewMinhasAtividades;
    private Toolbar myToolbar;

    private BottomNavigationView navigationView;
    private static AtividadeDBMemoriaDAO meuBanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMinhasAtividades = findViewById(R.id.lista_view_minhas_atividades);

        navigationView = findViewById(R.id.bottomNav);
        navigationView.setOnNavigationItemSelectedListener(this);

        myToolbar = findViewById(R.id.minhaToolbar);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Main");

        FloatingActionButton fab = findViewById(R.id.fab_add_atividade);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"ok",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this,CriarAtividadeActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        listViewMinhasAtividades.setAdapter(new MinhaAtividadeAdapter(this,getMinhaAtividades()));
    }

    private ArrayList<Atividade> getMinhaAtividades() {
        ArrayList<Atividade> atividades = new ArrayList<>();
        //ContatoDAO contatoDAO = new ContatoDAO(this);
        //contatos = contatoDAO.buscarContato();
        //meuBanco = AtividadeDBMemoriaDAO.getInstance();
        for (int i =0;i<10;i++){
            Atividade atividade = new Atividade();
            atividade.setDescricao( i +"dsadasdasdasdadasdasdasdaddddddddddddddddddddsadsa");
            atividade.setNome( i +"nome");
            atividades.add(atividade);
        }
        return  atividades;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home: {
                Toast.makeText(this, "home", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_add: {
                Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.menu_buscar: {
                Toast.makeText(this, "busca", Toast.LENGTH_SHORT).show();
                break;
            }
        }
        return false;
    }
}
