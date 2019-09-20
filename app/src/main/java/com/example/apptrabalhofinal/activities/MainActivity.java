package com.example.apptrabalhofinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptrabalhofinal.R;

public class MainActivity extends AppCompatActivity {
    private ImageView imgLogo;
    private Button btnLogin;
    private Button btnLoginGmail;
    private TextView txtCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLogo = findViewById(R.id.imgLogo);
        btnLogin = findViewById(R.id.btnCriar);
        btnLoginGmail = findViewById(R.id.btnLoginGmail);
        txtCriarConta = findViewById(R.id.txtCriarConta);

        txtCriarConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CadastroActivity.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
