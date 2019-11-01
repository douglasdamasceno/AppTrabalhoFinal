package com.example.apptrabalhofinal.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

//foto do perfil
   // private ImageView imgFoto;
   // private ImageView imgCamera;

    private Uri imageURI;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    private static final int PERMISSION_CODE_CAMERA = 1002;
    private static final int IMAGE_CAPTURE_CODE = 1003;

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
                //Toast.makeText(CadastroActivity.this,"camera",Toast.LENGTH_LONG).show();
                verificarVersaoAndroidCamera();
            }
        });
        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificarVersaoAndroidGalleria();
            }
        });

    }

    private void verificarVersaoAndroidGalleria(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                //permissão negada.
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                //show popuo for runtime permission
                requestPermissions(permissions,PERMISSION_CODE);
            }else{
                //permissão aceita
                pickImageFromGallery();
            }
        }else{
            //sistema abaixo do marshmallow
            pickImageFromGallery();
        }
        Toast.makeText(CadastroActivity.this,"galeria",Toast.LENGTH_LONG).show();
    }
    private void verificarVersaoAndroidCamera(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(Manifest.permission.CAMERA) ==
                    PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED){
                //permission not enabled, request it
                String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                //show popup to request permissions
                requestPermissions(permission, PERMISSION_CODE_CAMERA);
            }
            else {
                //permission already granted
                openCamera();
            }
        }
        else {
            //system os < marshmallow
            openCamera();
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }
    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        imageURI = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[0]== IMAGE_PICK_CODE) {
                    pickImageFromGallery();
                }else if(grantResults[0] == PackageManager.PERMISSION_DENIED ){
                    Toast.makeText(this, "Permissão negada galeria", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case PERMISSION_CODE_CAMERA:{
                if(grantResults.length >0 && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[1] == IMAGE_CAPTURE_CODE){
                    openCamera();
                }else if(grantResults[1] == PackageManager.PERMISSION_DENIED ){
                    Toast.makeText(this, "Permissão negada Camera", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:{
                Toast.makeText(this,"Permissão negada default!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageViewPerfil.setImageURI(data.getData());
        }else if (resultCode == RESULT_OK && requestCode == IMAGE_CAPTURE_CODE){
            imageViewPerfil.setImageURI(imageURI);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
