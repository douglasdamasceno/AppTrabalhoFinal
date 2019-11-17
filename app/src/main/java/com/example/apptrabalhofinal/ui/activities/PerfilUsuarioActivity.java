package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioFirebaseDAO;
import com.example.apptrabalhofinal.data.model.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private ImageView fotoPerfil;
    private EditText usernamePerfil;
    private EditText senhaPerfil;
    private EditText sexoPerfil;
    private TextView emailPerfil;
    private EditText idadePerfil;
    private Toolbar myToolbar;

    GoogleSignInAccount acct;
    private Button btnSaveMudancas;
   // String emailLogado;
    FirebaseUser userFirebase;

    Usuario usuarioAutentificado;
    UsuarioDAO usuarioDAOFirebase = UsuarioFirebaseDAO.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        userFirebase = usuarioDAOFirebase.getFirebaseUser();
        referenciaElementos();
        this.acct = GoogleSignIn.getLastSignedInAccount(this);

        verificarUsuarioAtentificado();
        if(userFirebase!=null) {
            usuarioAutentificado = usuarioDAOFirebase.getUserPorID(userFirebase.getUid());
            if(usuarioAutentificado!=null){
               Log.i("teste","testando buscat por email"+  usuarioAutentificado.toString());
            }
//            if (usuarioAutentificado.getMeuPerfil().getIdade() != null) {
//                idadePerfil.setText(usuarioAutentificado.getMeuPerfil().getIdade());
//            }
            //emailPerfil.setText("Email :" + usuarioAutentificado.getMeuPerfil().getEmail());
            emailPerfil.setText("Email :" + userFirebase.getEmail());
        }

    }
    public void verificarUsuarioAtentificado(){
            if(acct!=null){
                Glide.with(this).load(String.valueOf(acct.getPhotoUrl())).into(fotoPerfil);

                Log.i("teste","Logado gmail: "+ acct.getDisplayName());

                usernamePerfil.setText(acct.getDisplayName());
                senhaPerfil.setText("informe nova senha");

            }else if(userFirebase!=null) {

                // usuarioAutentificado = usuarioDAOFirebase.getUserPorID(userFirebase.getUid());
                Glide.with(this).load(String.valueOf(userFirebase.getPhotoUrl())).into(fotoPerfil);

//            Log.i("teste","Usuario Autentificado: "+ usuarioAutentificado.toString());
                //usernamePerfil.setText(usuarioAutentificado.getMeuPerfil().getNome());
                usernamePerfil.setText(userFirebase.getDisplayName());
                Log.i("teste","userFirebase: "+ userFirebase.getDisplayName());

                //senhaPerfil.setText(usuarioAutentificado.getMeuPerfil().getSenha());
                senhaPerfil.setText("informe nova senha");
                // sexoPerfil.setText(usuarioAutentificado.getMeuPerfil().getSexo());
            }
    }
    public void editarPerfil(View view) {
        if (!usernamePerfil.getText().toString().isEmpty()
                && !senhaPerfil.getText().toString().isEmpty()
        ) {
            usuarioAutentificado.getMeuPerfil().setSexo(sexoPerfil.getText().toString());
            usuarioAutentificado.getMeuPerfil().setNome(usernamePerfil.getText().toString());
            usuarioAutentificado.getMeuPerfil().setSenha(senhaPerfil.getText().toString());
            usuarioAutentificado.getMeuPerfil().setIdade(idadePerfil.getText().toString());
            usuarioDAOFirebase.editar(
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}