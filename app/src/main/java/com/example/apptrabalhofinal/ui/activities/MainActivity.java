package com.example.apptrabalhofinal.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeDBMemoriaDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDBMemoriaDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.data.model.Usuario;
import com.example.apptrabalhofinal.ui.adapter.MinhaAtividadeAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {


    Usuario usuarioAutentificado;
    FirebaseUser userFirebase;

    UsuarioDAO usuarioDAO = UsuarioDBMemoriaDAO.getInstance();
    AtividadeDAO atividadeDAO = AtividadeDBMemoriaDAO.getInstance();

    UsuarioDAO usuarioDAOFirebase = UsuarioFirebaseDAO.getInstance();

    private ListView listViewMinhasAtividades;
    private Toolbar myToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarElementos();

        userFirebase = usuarioDAOFirebase.getUsuarioAutentificado();
        if(userFirebase!=null) {
            Log.i("teste", "Main user firebase: " + userFirebase.getUid());
        }

        verificarAutentificacao();

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null) {
            String emailUser = bundle.getString("email");
            usuarioAutentificado = usuarioDAO.getUsuarioPorEmail(emailUser);
          //  Toast.makeText(this,usuarioAutentificado.toString(),Toast.LENGTH_LONG).show();
        }

        drawerLayout =(DrawerLayout)  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,myToolbar
                ,R.string.open_drawer,R.string.closer_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        AtualizarMinhasAtividades();
        AtualizarHeader();

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_menu_nav_participando: {
                        Intent intent = new Intent(getApplicationContext(),AtividadeParticipoActivity.class);
                        if(usuarioAutentificado!=null) {
                            intent.putExtra("email", usuarioAutentificado.getMeuPerfil().getEmail());
                        }
                        startActivity(intent);
                        break;
                    }case R.id.id_menu_nav_perfil: {
                        Intent intent = new Intent(getApplicationContext(),PerfilUsuarioActivity.class);
                        if(usuarioAutentificado!=null) {
                            intent.putExtra("email", usuarioAutentificado.getMeuPerfil().getEmail());
                        }
                        startActivity(intent);
                        break;
                    }case R.id.id_menu_nav_sair: {
                        logout();
                        break;
                    }case R.id.id_menu_nav_buscar: {
                        Intent intent = new Intent(getApplicationContext(), BuscarAtividadeListaActivity.class);
                        if(usuarioAutentificado!=null) {
                            intent.putExtra("email", usuarioAutentificado.getMeuPerfil().getEmail());
                        }
                        startActivity(intent);
                        break;
                    }
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab_add_atividade);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CriarAtividadeActivity.class);
                if(usuarioAutentificado!=null) {
                    String email = usuarioAutentificado.getMeuPerfil().getEmail();
                    intent.putExtra("email", email);
                }
                startActivity(intent);
            }
        });
        listViewMinhasAtividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                abriItem(i);
            }
        });
        listViewMinhasAtividades.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int itemSelecionado, long l) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                final String[] opacao = {"deleta"};

                alerta.setItems(opacao, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(opacao[i].equals("deleta")){
                            Toast.makeText(MainActivity.this,"Deletado",Toast.LENGTH_SHORT).show();
                            Atividade atividade = getMinhaAtividades().get(itemSelecionado);
                            if(atividadeDAO.remover(atividade.getId())){
                                AtualizarMinhasAtividades();
                                Toast.makeText(MainActivity.this,"Deletado com sucesso",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MainActivity.this,"Falha ao Deletar",Toast.LENGTH_SHORT).show();
                            }
                        }
                        Log.i("teste","saiu do if");
                    }
                }).create().show();

                return true;
            }
        });
    }

    void verificarAutentificacao(){
        if(userFirebase==null){
            Log.i("teste","Usuario não autentiicado");
            Intent intent = new Intent(this,InicialActivity.class);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"Usuario autenticado",Toast.LENGTH_LONG).show();
            Log.i("teste","Usuario autentiicado");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        AtualizarMinhasAtividades();
        AtualizarHeader();
    }
    void AtualizarHeader(){
        View headView = navigationView.getHeaderView(0);
        ImageView imgPerfil = (ImageView) headView.findViewById(R.id.id_nav_header_perfil_foto);
        TextView nomeUsusario = (TextView) headView.findViewById(R.id.id_nav_header_nome);
        TextView emailUsuario = (TextView) headView.findViewById(R.id.id_nav_header_email);
        if(usuarioAutentificado!=null) {
            nomeUsusario.setText(usuarioAutentificado.getMeuPerfil().getNome());
            emailUsuario.setText(usuarioAutentificado.getMeuPerfil().getEmail());
        }
        imgPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"foto perfil",Toast.LENGTH_LONG).show();
            }
        });
    }
    public ArrayList<Atividade> getMinhaAtividades() {
        ArrayList<Atividade> atividades = null;
        if(usuarioAutentificado!=null) {
            atividades = atividadeDAO.listarMinhasAtividades(usuarioAutentificado.getMeuPerfil().getEmail()); //new ArrayList<>();
        }
        return  atividades;
    }
    public void AtualizarMinhasAtividades(){
        listViewMinhasAtividades.setAdapter(new MinhaAtividadeAdapter(this,getMinhaAtividades()));
    }
    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    public void abriItem(int itemSelecionado) {
        if (getMinhaAtividades().size() > 0) {
            Intent intent = new Intent(this, AtividadeDetalheActivity.class);
            Atividade atividade = getMinhaAtividades().get(itemSelecionado);
            intent.putExtra("id", atividade.getId());
            Log.i("teste","email user:"+ usuarioAutentificado.getMeuPerfil());
            intent.putExtra("email", usuarioAutentificado.getMeuPerfil().getEmail());
            startActivity(intent);
        }
    }
    public void logout(){
        usuarioAutentificado = null;
        FirebaseAuth.getInstance().signOut();
        Log.i("teste","Logout");
        //userFirebase.signOut();
        startActivity(new Intent(this,LoginActivity.class));
        finish();
    }
    void inicializarElementos(){
        myToolbar = findViewById(R.id.minhaToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Minhas Atividades");
        listViewMinhasAtividades = findViewById(R.id.lista_view_minhas_atividades);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }
}
