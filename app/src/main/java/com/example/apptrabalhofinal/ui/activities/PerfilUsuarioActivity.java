package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDBMemoriaDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Usuario;
import com.google.firebase.auth.FirebaseUser;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private ImageView fotoPerfil;
    private EditText usernamePerfil;
    private EditText senhaPerfil;
    private EditText sexoPerfil;
    private TextView emailPerfil;
    private EditText idadePerfil;
    private Toolbar myToolbar;

    private Button btnSaveMudancas;
   // String emailLogado;
    FirebaseUser userFirebase;

    Usuario usuarioAutentificado;
    UsuarioDAO usuarioDAO = UsuarioDBMemoriaDAO.getInstance();
    UsuarioDAO usuarioDAOFirebase = UsuarioFirebaseDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        userFirebase = usuarioDAOFirebase.getFirebaseUser();
        referenciaElementos();
        verificarUsuarioAtentificado();
        if(userFirebase!=null) {
            if (usuarioAutentificado.getMeuPerfil().getIdade() != null) {
                idadePerfil.setText(usuarioAutentificado.getMeuPerfil().getIdade());
            }
            emailPerfil.setText("Email :" + usuarioAutentificado.getMeuPerfil().getEmail());
        }
    }
    public void verificarUsuarioAtentificado(){
            usuarioAutentificado = usuarioDAO.getUsuarioPorEmail(userFirebase.getEmail());
            fotoPerfil.setImageURI(userFirebase.getPhotoUrl());
            usernamePerfil.setText(usuarioAutentificado.getMeuPerfil().getNome());
            senhaPerfil.setText(usuarioAutentificado.getMeuPerfil().getSenha());
            sexoPerfil.setText(usuarioAutentificado.getMeuPerfil().getSexo());
    }
    public void editarPerfil(View view) {
        if (!usernamePerfil.getText().toString().isEmpty()
                && !senhaPerfil.getText().toString().isEmpty()
        ) {
            usuarioAutentificado.getMeuPerfil().setSexo(sexoPerfil.getText().toString());
            usuarioAutentificado.getMeuPerfil().setNome(usernamePerfil.getText().toString());
            usuarioAutentificado.getMeuPerfil().setSenha(senhaPerfil.getText().toString());
            usuarioAutentificado.getMeuPerfil().setIdade(idadePerfil.getText().toString());
            usuarioDAO.editar(
                    usuarioAutentificado.getMeuPerfil().getEmail(),
                    usuarioAutentificado.getMeuPerfil().getNome(),
                    usuarioAutentificado.getMeuPerfil().getSenha(),
                    usuarioAutentificado.getMeuPerfil().getIdade(),
                    usuarioAutentificado.getMeuPerfil().getSexo()
            );
            Toast.makeText(this, "Alterações salvas", Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(this, "Nao pode deixar campo vazio", Toast.LENGTH_LONG).show();
        }
    }
    public void referenciaElementos(){
        fotoPerfil = findViewById(R.id.perfil_foto);
        usernamePerfil = findViewById(R.id.perfil_nome);
        senhaPerfil = findViewById(R.id.perfil_senha);
        emailPerfil = findViewById(R.id.perfil_email);
        idadePerfil = findViewById(R.id.perfil_idade);
        sexoPerfil = findViewById(R.id.perfil_sexo);

        myToolbar = findViewById(R.id.minhaToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Perfil");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}