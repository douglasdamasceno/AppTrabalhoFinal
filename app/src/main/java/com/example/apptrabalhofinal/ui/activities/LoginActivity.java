package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;



public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail;
    private  EditText inputSenha;
    private Button btnLogin;
    private Toolbar myToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);
        btnLogin = findViewById(R.id.idBtnLogin);

        myToolbar = (Toolbar) findViewById(R.id.minhaToolbar);


        setSupportActionBar(myToolbar);
        //myToolbar.setTitle("Loginxx");
        getSupportActionBar().setTitle("Login");
       // ActionBar actionBar = getSupportActionBar();
       // actionBar.setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //aparecer seta.
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setDisplayShowCustomEnabled(true);


        //Pegando intent
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            //intente putExtra data
            String pEmail = bundle.getString("email");
            String pSenha = bundle.getString("senha");

            inputEmail.setText(pEmail);
            inputSenha.setText(pSenha);
        }




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
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
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
