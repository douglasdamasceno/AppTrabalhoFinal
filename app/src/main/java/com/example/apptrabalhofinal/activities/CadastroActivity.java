package com.example.apptrabalhofinal.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

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


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Cadastro");

        //aparecer seta.
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
