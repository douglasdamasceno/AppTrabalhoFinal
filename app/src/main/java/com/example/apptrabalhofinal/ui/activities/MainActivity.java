package com.example.apptrabalhofinal.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private ListView listViewMinhasAtividades;
    private Toolbar myToolbar;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;

    AtividadeDAO atividadeDAO = AtividadeDBMemoriaDAO.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewMinhasAtividades = findViewById(R.id.lista_view_minhas_atividades);
        myToolbar = findViewById(R.id.minhaToolbar);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Main");


        drawerLayout =(DrawerLayout)  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,myToolbar
                ,R.string.open_drawer,R.string.closer_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.idAtualizar: {
                        Toast.makeText(MainActivity.this, "Atualizar", Toast.LENGTH_SHORT).show();
                        Log.i("click","atualizar");
                        break;
                    }
                    case R.id.idPerfilMenu: {
                        startActivity(new Intent(getApplicationContext(),PerfilUsuarioActivity.class));
                        break;
                    }
                    case R.id.idSairMenu: {
                        Toast.makeText(MainActivity.this, "Atualizar", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home: {
                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.menu_perfil: {
                        Toast.makeText(MainActivity.this, "Perfil", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.menu_buscar: {
                        Toast.makeText(MainActivity.this, "busca", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,MapsActivity.class));
                        break;
                    }
                }
                return true;
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab_add_atividade);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        ArrayList<Atividade> atividades = atividadeDAO.listarAtividadesTodos(); //new ArrayList<>();
        //ContatoDAO contatoDAO = new ContatoDAO(this);
        //contatos = contatoDAO.buscarContato();
        //meuBanco = AtividadeDBMemoriaDAO.getInstance();
//        for (int i =0;i<10;i++){
//            Atividade atividade = new Atividade();
//            atividade.setDescricao( i +"dsadasdasdasdadasdasdasdaddddddddddddddddddddsadsa");
//            atividade.setNome( i +"nome");
//            atividades.add(atividade);
//        }
        return  atividades;
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

}
