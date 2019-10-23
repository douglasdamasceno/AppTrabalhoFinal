package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.present.interfaces.ContratoLogin;
import com.example.apptrabalhofinal.present.PresentLogin;


public class LoginActivity extends AppCompatActivity  implements ContratoLogin.view {

    private EditText inputEmail;
    private  EditText inputSenha;
    private Button btnLogin;
    private Toolbar myToolbar;

    private String idUser;

    private PresentLogin presentLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputSenha = findViewById(R.id.inputSenha);
        btnLogin = findViewById(R.id.idBtnLogin);

        myToolbar = (Toolbar) findViewById(R.id.minhaToolbar);


        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("ContratoLogin");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presentLogin = new PresentLogin(this);

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

                presentLogin.validarLogin(email,senha);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void formatoInvalidoEmail() {
        inputEmail.setFocusable(true);
        inputEmail.setError("Email Invalido");
    }

    @Override
    public void senhaInvalida() {
        inputSenha.setFocusable(true);
        inputSenha.setError("Senha deve ser maior que 6");
    }

    @Override
    public void realizarlogin(String email) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("email",email);
        startActivity(intent);
    }

    @Override
    public void usuarioComEmailInvalido() {
        inputEmail.setFocusable(true);
        inputEmail.setError("Email não encontrado");
    }

    @Override
    public void usuarioComSenha() {
        inputSenha.setFocusable(true);
        inputSenha.setError("Senha errada");
    }

}
