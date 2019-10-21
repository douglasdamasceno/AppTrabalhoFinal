package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.apptrabalhofinal.R;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private ImageView fotoPerfil;
    private EditText usernamePerfil;
    private EditText senhaPerfil;
    private EditText emailPerfil;
    private RadioButton sexoPerfil;
    private EditText idadePerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);

        fotoPerfil = findViewById(R.id.perfil_foto);
        usernamePerfil = findViewById(R.id.perfil_nome);
        senhaPerfil = findViewById(R.id.perfil_senha);
        emailPerfil = findViewById(R.id.perfil_email);
        idadePerfil = findViewById(R.id.perfil_idade);

        usernamePerfil.setText("douglas");
        senhaPerfil.setText("12345");
        emailPerfil.setText("dofo@gams.com");
        idadePerfil.setText("24");
    }

}
