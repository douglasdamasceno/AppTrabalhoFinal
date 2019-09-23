package com.example.apptrabalhofinal.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaCodec;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;

import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private  EditText inputSenha;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);
        btnLogin = findViewById(R.id.idBtnLogin);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
        //aparecer seta.
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String senha = inputSenha.getText().toString();
                //patterns de email.
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    inputEmail.setError("Email Invalido");
                    inputEmail.setFocusable(true);
                }else if(senha.length()<6){
                    inputSenha.setFocusable(true);
                    inputSenha.setError("Senha deve ser maior que 6");
                }else{
                    Toast.makeText(LoginActivity.this,"okk",Toast.LENGTH_SHORT).show();
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
