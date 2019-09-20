package com.example.apptrabalhofinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.apptrabalhofinal.R;


public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private  EditText inputSenha;
    private Button btnLogin;
    private Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);

    }
}
