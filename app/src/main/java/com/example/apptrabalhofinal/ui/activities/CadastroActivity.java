package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;

public class CadastroActivity extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputEmail;
    private  EditText inputSenha;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inputUsername = findViewById(R.id.inputUsernameCadastro);
        inputEmail = findViewById(R.id.inputEmailCadastro);
        inputSenha = findViewById(R.id.inputSenhaCadastro);
        btnLogin = findViewById(R.id.btnCadastro);


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
