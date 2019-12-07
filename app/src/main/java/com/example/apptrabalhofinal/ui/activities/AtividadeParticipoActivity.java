package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.AtividadeDAO;
import com.example.apptrabalhofinal.data.dao.AtividadeFirebaseDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Atividade;
import com.example.apptrabalhofinal.ui.adapter.MinhaAtividadeAdapter;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class AtividadeParticipoActivity extends AppCompatActivity {

    UsuarioFirebaseDAO usuarioFirebaseDAO = UsuarioFirebaseDAO.getInstance();
    AtividadeFirebaseDAO atividadeDAO = AtividadeFirebaseDAO.getInstance();

    FirebaseUser userFirebase;
    private Toolbar myToolbar;
    private ListView listViewMinhasAtividades;

    private String emailProprietario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atividade_participo);

        inicializarElementos();
        userFirebase = usuarioFirebaseDAO.getFirebaseUser();

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
            intent.putExtra("nome", atividade.getNome());
            intent.putExtra("descricao", atividade.getDescricao());
            intent.putExtra("quantidade",Integer.toString( atividade.getVagasParticipantes()));
            intent.putExtra("idade", atividade.getIdadePublico());
            intent.putExtra("sexo", atividade.getSexoPublico());
            intent.putExtra("data", atividade.getData());
            intent.putExtra("hora", atividade.getHora());
            intent.putExtra("tipo", atividade.getTipoDeAtividade());
            intent.putExtra("totalParticantes", Integer.toString(atividade.getMeusParticipantes().size()));

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
        getSupportActionBar().setTitle("Atividades Participo");
        listViewMinhasAtividades = findViewById(R.id.lista_view_atividades_participo);
    }
}
