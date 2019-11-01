package com.example.apptrabalhofinal.data.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.apptrabalhofinal.data.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.ContentValues.TAG;

public class UsuarioFirebaseDAO implements UsuarioDAO {

    static UsuarioFirebaseDAO usuarioFirebaseDAO;

    private FirebaseAuth mAuth;
    private FirebaseFirestore database;
    private FirebaseUser user;

    private UsuarioFirebaseDAO(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
    }

    public static UsuarioFirebaseDAO getInstance(){
        if(usuarioFirebaseDAO==null){
            usuarioFirebaseDAO = new UsuarioFirebaseDAO();
        }
        return usuarioFirebaseDAO;
    }

    @Override
    public void addNovo(final String username, final String email, final String senha) {
        mAuth.createUserWithEmailAndPassword(email,senha)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Log.i("teste","sucesso id do usuriao: "+ authResult.getUser().getUid());
                    String id = authResult.getUser().getUid();
                    salvaInformacaoUsuario(id,username,email,senha);

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("teste","falha ao criar conta"+ e.getMessage());
                }
            }) ;
    }

    void salvaInformacaoUsuario(String id,String username,String email,String senha){
        Usuario usuario = new Usuario(id,username,email,senha) ;

        database.collection("user")
                .add(usuario)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("teste","salvo no db" + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("teste","falha ao salvo no db"+ e.getMessage());
                    }
                });
    }


    @Override
    public void editar(String email, String username, String senha, String idade, String sexo) {

    }

    @Override
    public void remover(int id) {

    }

    @Override
    public void getUsuario(int id) {

    }

    @Override
    public boolean getLogin(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email,senha)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.i("teste","getLogin com sucesso: " + authResult.getUser().toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("teste","falha ao fazer login: "+ e.getMessage());
                    }
                });

        this.user = FirebaseAuth.getInstance().getCurrentUser();

        if(this.user!=null) {
            Log.i("teste","Login com sucesso e retornou true");
            return true;
        }
        return false;
    }

    @Override
    public boolean getEmailUsuario(String email) {
        return false;
    }

    @Override
    public Usuario getUsuarioPorEmail(String email) {
        return null;
    }

    @Override
    public String getIDporEmail(String email) {
        return null;
    }

    @Override
    public Usuario getUserPorID(String id) {
        return null;
    }

    @Override
    public FirebaseUser getFirebaseUser() {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        return user;
    }

}
