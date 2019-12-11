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
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeFirebaseDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.ui.adapter.MinhaAtividadeAdapter;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    FirebaseUser userFirebase;

    AtividadeDAO atividadeDAO = AtividadeFirebaseDAO.getInstance();

   UsuarioDAO usuarioDAOFirebase = UsuarioFirebaseDAO.getInstance();
   ArrayList<Atividade> minhasAtividade = new ArrayList<Atividade>();

    private ListView listViewMinhasAtividades;
    private Toolbar myToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    MinhaAtividadeAdapter atividadeAdapter;

    GoogleSignInAccount acct;
    private GoogleSignInClient mGoogleSignInClient;

    int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selected = -1;
        userFirebase = usuarioDAOFirebase.getFirebaseUser();
        inicializarElementos();

        verificarAutentificacao();
        criarMinhasAtividades();
        atualizarHeader();


        drawerLayout =(DrawerLayout)  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,myToolbar
                ,R.string.open_drawer,R.string.closer_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.id_menu_nav_participando: {
                        Intent intent = new Intent(getApplicationContext(),AtividadeParticipoActivity.class);
                        if(userFirebase!=null) {
                            intent.putExtra("email", userFirebase.getEmail());
                        }
                        startActivity(intent);
                        break;
                    }case R.id.id_menu_nav_perfil: {
                        Intent intent = new Intent(getApplicationContext(),PerfilUsuarioActivity.class);
                        if(userFirebase!=null) {
                            intent.putExtra("email",userFirebase.getEmail());
                        }
                        startActivity(intent);
                        break;
                    }case R.id.id_menu_nav_proxima: {
                        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
                        startActivity(intent);
                        break;
                    }case R.id.id_menu_nav_sair: {
                        logout();
                        break;
                    }case R.id.id_menu_nav_buscar: {
                        Intent intent = new Intent(getApplicationContext(), BuscarAtividadeListaActivity.class);
                        if(userFirebase!=null) {
                            intent.putExtra("email", userFirebase.getEmail());
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
                if(userFirebase!=null) {
                    String email = userFirebase.getEmail();
                    intent.putExtra("email", email);
                }
                startActivity(intent);
            }
        });

        listViewMinhasAtividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selected = i;
                abriItem();
            }
        });

        listViewMinhasAtividades.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int itemSelecionado, long l) {
                AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                final String[] opcao = {"Deleta"};
                alerta.setItems(opcao, new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(opcao[i].equals("Deleta")){
                            selected = itemSelecionado;
                            Atividade atividade = getMinhaAtividades().get(selected);
                            selected = -1;
                            atividadeDAO.remover(atividade.getId());
                            notificarAdapterMinhasAtividade();
                            Toast.makeText(MainActivity.this,"Deletado com sucesso",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).create().show();
                //notificarAdapterMinhasAtividade();
                return true;
            }
        });
    }

    void verificarAutentificacao(){
        if(userFirebase==null){
            Intent intent = new Intent(this,InicialActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.acct = GoogleSignIn.getLastSignedInAccount(this);
        notificarAdapterMinhasAtividade();
        selected = -1;
        atualizarHeader();
    }

    @Override
    protected void onStart() {
        super.onStart();
        selected = -1;
        this.acct = GoogleSignIn.getLastSignedInAccount(this);
    }

    void atualizarHeader(){
        View headView = navigationView.getHeaderView(0);
        ImageView imgPerfil = (ImageView) headView.findViewById(R.id.id_nav_header_perfil_foto);
        TextView nomeUsusario = (TextView) headView.findViewById(R.id.id_nav_header_nome);
        TextView emailUsuario = (TextView) headView.findViewById(R.id.id_nav_header_email);

        if(userFirebase!=null){
            Log.i("teste","usuario autentificado userFirebase ok");

            nomeUsusario.setText(userFirebase.getDisplayName());
            emailUsuario.setText(userFirebase.getEmail());
            imgPerfil.setImageURI(userFirebase.getPhotoUrl());

            Glide.with(this).load(String.valueOf(userFirebase.getPhotoUrl())).into(imgPerfil);

        }
        if (this.acct != null) {
            String personName = this.acct.getDisplayName();
            String personEmail = this.acct.getEmail();
            Uri personPhoto = this.acct.getPhotoUrl();

            nomeUsusario.setText(personName);
            emailUsuario.setText(personEmail);
            Glide.with(this).load(String.valueOf(personPhoto)).into(imgPerfil);
         }
    }

    private ArrayList<Atividade> getMinhaAtividades() {
        ArrayList<Atividade> atividades = null;
        if(userFirebase!=null) {
            atividades = atividadeDAO.listarMinhasAtividades(userFirebase.getEmail());
        }
        return  atividades;
    }

    private void criarMinhasAtividades(){
        minhasAtividade = getMinhaAtividades();
        atividadeAdapter = new MinhaAtividadeAdapter(this,minhasAtividade);
        listViewMinhasAtividades.setAdapter(atividadeAdapter);
    }
    private void notificarAdapterMinhasAtividade(){
        atividadeAdapter.notifyDataSetChanged();
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
        if (getMinhaAtividades().size() > 0  && selected >=0) {
            Intent intent = new Intent(this, AtividadeDetalheActivity.class);
            Atividade atividade = getMinhaAtividades().get(selected);
            selected = -1;
            intent.putExtra("id", atividade.getId());
            intent.putExtra("nome", atividade.getNome());
            intent.putExtra("descricao", atividade.getDescricao());
            intent.putExtra("quantidade", Integer.toString(atividade.getVagasParticipantes()));
            intent.putExtra("data", atividade.getData());
            intent.putExtra("hora", atividade.getHora());
            intent.putExtra("tipo", atividade.getTipoDeAtividade());
            intent.putExtra("idade", atividade.getIdadePublico());
            intent.putExtra("sexo", atividade.getSexoPublico());

            intent.putExtra("cidade",atividade.getEndereco().getCidade());
            intent.putExtra("rua",atividade.getEndereco().getRua());
            intent.putExtra("estado",atividade.getEndereco().getEstado());
            intent.putExtra("cep",atividade.getEndereco().getCep());
            intent.putExtra("complemento",atividade.getEndereco().getComplemento());


            startActivity(intent);
        }

    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        if(this.acct!=null){
            Log.i("teste","logut do google entrou");
            referenciarElementoGmail();
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        }
                    });
        }
        Log.i("teste","Logout feito");
        startActivity(new Intent(this,InicialActivity.class));
        finish();
    }
    void inicializarElementos(){
        myToolbar = findViewById(R.id.minhaToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Minhas Atividades");
        listViewMinhasAtividades = findViewById(R.id.lista_view_minhas_atividades);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }
    void referenciarElementoGmail(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }
}
