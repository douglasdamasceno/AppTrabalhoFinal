package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.present.PresentCadastro;
import com.example.apptrabalhofinal.present.interfaces.ContratoCadastro;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CadastroActivity extends AppCompatActivity  implements ContratoCadastro.view {

    private EditText inputUsername;
    private EditText inputEmail;
    private  EditText inputSenha;
    private Button btnLogin;
    private ImageView imageViewPerfil;

    private Toolbar myToolbar;

    BottomSheetDialog bottomSheetDialog;
    ContratoCadastro.present presentCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inicializarElementos();
        mostarViewFotoPerfil();


        imageViewPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentCadastro.validarCadastro(inputUsername.getText().toString(),
                        inputEmail.getText().toString(),
                        inputSenha.getText().toString());
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void formatoUsernameInvalido() {
        inputUsername.setFocusable(true);
        inputUsername.setError("Username deve ser maior que 5");
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
    public void realizarCadastro() {
        Intent intent = new Intent(CadastroActivity.this,LoginActivity.class);
        intent.putExtra("email", inputEmail.getText().toString());
        intent.putExtra("senha", inputSenha.getText().toString());
        startActivity(intent);
        finish();
    }

    public void inicializarElementos(){
        presentCadastro = new PresentCadastro(CadastroActivity.this);
        inputUsername = findViewById(R.id.inputUsernameCadastro);
        inputEmail = findViewById(R.id.inputEmailCadastro);
        inputSenha = findViewById(R.id.inputSenhaCadastro);
        btnLogin = findViewById(R.id.btnCadastro);

        myToolbar = findViewById(R.id.minhaToolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Cadastro");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewPerfil = findViewById(R.id.imageFotoPerfil);
    }

    public void mostarViewFotoPerfil(){
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

    }

}
