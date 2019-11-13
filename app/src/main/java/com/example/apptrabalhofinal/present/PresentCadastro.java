package com.example.apptrabalhofinal.present;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.apptrabalhofinal.data.dao.UsuarioDAO;
import com.example.apptrabalhofinal.data.dao.UsuarioFirebaseDAO;
import com.example.apptrabalhofinal.present.interfaces.ContratoCadastro;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class PresentCadastro implements ContratoCadastro.present {



    private ContratoCadastro.view cadastroActivity;
    UsuarioDAO usuarioFirebaseDAO = UsuarioFirebaseDAO.getInstance();

    public PresentCadastro(ContratoCadastro.view cadastroActivity){
        this.cadastroActivity = cadastroActivity;
    }

    @Override
    public void validarCadastro(Uri fotoPerfil, String username, String email, String senha) {
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            cadastroActivity.formatoInvalidoEmail();
        }
        if(senha.length()<6){
            cadastroActivity.senhaInvalida();
        }
        if(username.length()<6){
            cadastroActivity.formatoUsernameInvalido();
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches() && senha.length()>=6 && username.length()>=6){
            usuarioFirebaseDAO.addNovo(fotoPerfil,username,email,senha);
           // salvaFotoDoUsuario(fotoPerfil);
            cadastroActivity.realizarCadastro();
        }
    }
    void salvaFotoDoUsuario(Uri fotoPerfil){
        String filename = UUID.randomUUID().toString();
        //usuadao
        final StorageReference riversRef = FirebaseStorage.getInstance().getReference("imagens"+filename);
        riversRef.putFile(fotoPerfil)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.i("teste","fot: "+ uri.toString());
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                    }
                });
    }





}
