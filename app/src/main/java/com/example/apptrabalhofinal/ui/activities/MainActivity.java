package com.example.apptrabalhofinal.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeDBMemoriaDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDBMemoriaDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.data.model.Usuario;
import com.example.apptrabalhofinal.ui.adapter.MinhaAtividadeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {


    Usuario usuarioAutentificado;
    UsuarioDAO usuarioDAO = UsuarioDBMemoriaDAO.getInstance();

    private ListView listViewMinhasAtividades;
    private Toolbar myToolbar;
    private DrawerLayout drawerLayout;
    //private BottomNavigationView bottomNavigationView;
    private NavigationView navigationView;

    private int itemSelecionado = -1;

    AtividadeDAO atividadeDAO = AtividadeDBMemoriaDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String emailUser = getIntent().getExtras().getString("email");
        usuarioAutentificado = usuarioDAO.getUsuarioPorEmail(emailUser);

        listViewMinhasAtividades = findViewById(R.id.lista_view_minhas_atividades);
        myToolbar = findViewById(R.id.minhaToolbar);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Minhas Atividades");


        drawerLayout =(DrawerLayout)  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,myToolbar
                ,R.string.open_drawer,R.string.closer_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Toast.makeText(this,usuarioAutentificado.toString(),Toast.LENGTH_LONG).show();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.id_menu_nav_home: {
                           // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        break;
                    }
                    case R.id.id_menu_nav_perfil: {
                        Intent intent = new Intent(getApplicationContext(),PerfilUsuarioActivity.class);
                        intent.putExtra("email",usuarioAutentificado.getMeuPerfil().getEmail());
                        startActivity(intent);
                        break;
                    }
                    case R.id.id_menu_nav_sair: {
                        //fazer logut
                        //Toast.makeText(MainActivity.this, "sair", Toast.LENGTH_SHORT).show();
                        logout();
                        break;
                    }
                    case R.id.id_menu_nav_buscar: {
                        //startActivity(new Intent(getApplicationContext(),MapsActivity.class));
                        startActivity(new Intent(getApplicationContext(), BuscarAtividadeListaActivity.class));
                        break;
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


//        bottomNavigationView = findViewById(R.id.bottomNav);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.menu_home: {
//                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    case R.id.menu_perfil: {
//                        Toast.makeText(MainActivity.this, "Perfil", Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    case R.id.menu_buscar: {
//                        Toast.makeText(MainActivity.this, "busca", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(),MapsActivity.class));
//                        break;
//                    }
//                }
//                return true;
//            }
//        });


        FloatingActionButton fab = findViewById(R.id.fab_add_atividade);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CriarAtividadeActivity.class);
                String email = usuarioAutentificado.getMeuPerfil().getEmail();
                intent.putExtra("email",email);
                startActivity(intent);
            }
        });

        listViewMinhasAtividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                itemSelecionado = i;
                abriItem();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        listViewMinhasAtividades.setAdapter(new MinhaAtividadeAdapter(this,getMinhaAtividades()));
    }

    private ArrayList<Atividade> getMinhaAtividades() {
        ArrayList<Atividade> atividades = atividadeDAO.listarMinhasAtividades(usuarioAutentificado.getMeuPerfil().getEmail()); //new ArrayList<>();
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

    public void abriItem() {
        if (getMinhaAtividades().size() > 0) {
            Intent intent = new Intent(this, AtividadeDetalheActivity.class);
            //fazer o put do id data atividade para pegar a atividade pelo id no dao.
            Atividade atividade = getMinhaAtividades().get(itemSelecionado);
            intent.putExtra("id", atividade.getId());
            intent.putExtra("nome", atividade.getNome());
            intent.putExtra("descricao", atividade.getDescricao());

            startActivity(intent);
        }
    }

    public void logout(){
        usuarioAutentificado = null;
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
}
