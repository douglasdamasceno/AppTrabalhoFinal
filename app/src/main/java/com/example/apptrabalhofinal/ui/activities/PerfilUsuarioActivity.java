package com.example.apptrabalhofinal.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptrabalhofinal.R;
import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioDBMemoriaDAO;
import com.example.apptrabalhofinal.data.model.Usuario;

public class PerfilUsuarioActivity extends AppCompatActivity {

    private ImageView fotoPerfil;
    private EditText usernamePerfil;
    private EditText senhaPerfil;
    private TextView emailPerfil;

    private EditText idadePerfil;

    private Toolbar myToolbar;

    private Button btnSaveMudancas;

    private RadioButton rbMasculino;
    private RadioButton rbFeminino;

    Usuario usuarioAutentificado;
    UsuarioDAO usuarioDAO = UsuarioDBMemoriaDAO.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuario);



        fotoPerfil = findViewById(R.id.perfil_foto);
        usernamePerfil = findViewById(R.id.perfil_nome);
        senhaPerfil = findViewById(R.id.perfil_senha);
        emailPerfil = findViewById(R.id.perfil_email);
        idadePerfil = findViewById(R.id.perfil_idade);

        rbMasculino = findViewById(R.id.sexo_masculino);
        rbFeminino = findViewById(R.id.sexo_feminino);

        String emailUser = getIntent().getExtras().getString("email");
        usuarioAutentificado = usuarioDAO.getUsuarioPorEmail(emailUser);


        usernamePerfil.setText(usuarioAutentificado.getMeuPerfil().getNome());
        senhaPerfil.setText(usuarioAutentificado.getMeuPerfil().getSenha());
        emailPerfil.setText("Email :" + usuarioAutentificado.getMeuPerfil().getEmail());

         myToolbar = findViewById(R.id.minhaToolbar);

        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public void editarPerfil(View view){
        if(!usernamePerfil.getText().toString().isEmpty()
                && !senhaPerfil.getText().toString().isEmpty()
                && !emailPerfil.getText().toString().isEmpty()
        ){
            usuarioAutentificado.getMeuPerfil().setSexo(radioSelecionado());
            usuarioDAO.editar(
                    usuarioAutentificado.getMeuPerfil().getEmail(),
                    usuarioAutentificado.getMeuPerfil().getNome(),
                    usuarioAutentificado.getMeuPerfil().getSenha(),
                    usuarioAutentificado.getMeuPerfil().getIdade(),
                    usuarioAutentificado.getMeuPerfil().getSexo()
            );
            Toast.makeText(this,"Alterações salvas",Toast.LENGTH_LONG).show();
            finish();
        }else {
            Toast.makeText(this,"Nao pode deixar campo vazio",Toast.LENGTH_LONG).show();
        }
    }

    public String  radioSelecionado(){
        String result = "Nao selecionado";
        if(rbMasculino.isChecked()){
            result = rbMasculino.getText().toString();
        }else if(rbFeminino.isChecked()){
            result = rbFeminino.getText().toString();
        }
        return  result;
    }

}
