package com.example.apptrabalhofinal.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioFirebaseDAO;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class InicialActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 2000;
    //private ImageView imgLogo;

    private FirebaseAuth mAuth;
    private Button btnLogin;
    private Button btnLoginGmail;
    private TextView txtCriarConta;

    private GoogleSignInClient mGoogleSignInClient;

    UsuarioDAO usuarioDAOFirebase = UsuarioFirebaseDAO.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);

        inicializarElementos();
        mAuth = FirebaseAuth.getInstance();
        referenciarElementoGmail();

        txtCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InicialActivity.this,CadastroActivity.class));
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InicialActivity.this,LoginActivity.class));
            }
        });
        btnLoginGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    void inicializarElementos(){
        btnLogin = findViewById(R.id.btnCriar);
        btnLoginGmail = findViewById(R.id.btnLoginGmail);
        txtCriarConta = findViewById(R.id.txtCriarConta);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("teste", "Google sign in failed" + e.getMessage());
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.e("teste", "Antes firebaseAuthWithGoogle: " + acct.getId());
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
//                            Usuario usuario = new Usuario();
//                            usuario.setId(user.getUid());
//                            usuario.getMeuPerfil().setNome(user.getDisplayName());
//                            usuario.getMeuPerfil().setEmail(user.getEmail());
//                            usuario.getMeuPerfil().setIdPerfil(user.getUid());
//                            //usuario.getMeuPerfil().setFotoPerfil(user.getPhotoUrl());
                           // usuarioDAOFirebase.addNovo(user.getPhotoUrl().toString(),user.getDisplayName(),user.getEmail(),"");

                            Intent intent = new Intent(InicialActivity.this,MainActivity.class);
                            startActivity(intent);

                            Log.d("teste", "firebaseAuthWithGoogle com sucesso");
                            finish();
                        } else {
                            Toast.makeText(InicialActivity.this,"Falha ao logar",Toast.LENGTH_SHORT).show();
                            Log.d("teste", "firebaseAuthWithGoogle com falha de");
                        }

                    }
                });
    }

    void referenciarElementoGmail(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

}
