package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CadastroActivity extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputEmail;
    private  EditText inputSenha;
    private Button btnLogin;
    private ImageView imageViewPerfil;

    private Toolbar myToolbar;

    BottomSheetDialog bottomSheetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inputUsername = findViewById(R.id.inputUsernameCadastro);
        inputEmail = findViewById(R.id.inputEmailCadastro);
        inputSenha = findViewById(R.id.inputSenhaCadastro);
        btnLogin = findViewById(R.id.btnCadastro);
        myToolbar = findViewById(R.id.minhaToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Cadastro");

        imageViewPerfil = findViewById(R.id.imageFotoPerfil);

        bottomSheetDialog = new BottomSheetDialog(CadastroActivity.this);
        View menu_foto = getLayoutInflater().inflate(R.layout.dialog_foto_fragmento,null);
        bottomSheetDialog.setContentView(menu_foto);

        View camera = menu_foto.findViewById(R.id.id_camera);
        View galeria = menu_foto.findViewById(R.id.id_galeria);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CadastroActivity.this,"camera",Toast.LENGTH_LONG).show();
            }
        });
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CadastroActivity.this,"galeria",Toast.LENGTH_LONG).show();
            }
        });

        imageViewPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
            }
        });

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("Cadastro");

        //aparecer seta.
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowCustomEnabled(true);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = inputUsername.getText().toString();
                String email = inputEmail.getText().toString();
                String senha = inputSenha.getText().toString();
                //patterns de email.
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    inputEmail.setError("Email Invalido");
                    inputEmail.setFocusable(true);
                }else if(senha.length()<6){
                    inputSenha.setFocusable(true);
                    inputSenha.setError("Senha deve ser maior que 6");
                }else if(username.length()<3){
                    inputUsername.setFocusable(true);
                    inputUsername.setError("username deve ser maior que 3");
                }else{
                    Toast.makeText(CadastroActivity.this,"okk",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CadastroActivity.this,LoginActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("senha",senha);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
