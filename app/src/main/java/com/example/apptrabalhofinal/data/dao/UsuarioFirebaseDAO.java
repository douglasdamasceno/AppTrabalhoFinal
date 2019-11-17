package com.example.apptrabalhofinal.data.dao;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apptrabalhofinal.data.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;


public class UsuarioFirebaseDAO implements UsuarioDAO {

    static UsuarioFirebaseDAO usuarioFirebaseDAO;
    private Usuario usuarioRetornado;

    private FirebaseAuth mAuth;
    private FirebaseFirestore database;
    private FirebaseUser user;
    private StorageReference mStorageRef;

    private UsuarioFirebaseDAO(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        usuarioRetornado = null;
    }

    public static UsuarioFirebaseDAO getInstance(){
        if(usuarioFirebaseDAO==null){
            usuarioFirebaseDAO = new UsuarioFirebaseDAO();
        }
        return usuarioFirebaseDAO;
    }

    @Override
    public void addNovo(final Uri fotoPerfil, final String username, final String email, final String senha) {
        mAuth.createUserWithEmailAndPassword(email,senha)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Log.i("teste","sucesso id do usuriao: "+ authResult.getUser().getUid());
                    String id = authResult.getUser().getUid();
                    salvaInformacaoUsuario("fotoPerfil",id,username,email,senha);
                    //salvaFotoDoUsuario(fotoPerfil);
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("teste","falha ao criar conta"+ e.getMessage());
                }
            }) ;
    }

    void salvaInformacaoUsuario(String fotoPerfil,String id,String username,String email,String senha){
        Usuario usuario = new Usuario(id,fotoPerfil,username,email,senha) ;

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
    void salvaFotoDoUsuario(Uri fotoPerfil){
        String filename = UUID.randomUUID().toString();
        final StorageReference riversRef = mStorageRef.getStorage().getReference("sss"+filename);
        //FirebaseStorage.getInstance().getReference("imagens"+filename);
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
                        //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
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
        DocumentReference docRef = database.collection("user").document(email);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        usuarioRetornado = document.toObject(Usuario.class);
                        Log.d("teste", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("teste", "No such document");
                    }
                } else {
                    Log.d("teste", "get failed with ", task.getException());
                }
            }
        });
        return this.usuarioRetornado;
    }


    @Override
    public Usuario getUserPorID(String id) {
        DocumentReference docRef = database.collection("user").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        usuarioRetornado = document.toObject(Usuario.class);
                        Log.d("teste", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("teste", "Não encontrado usuario No such document");
                    }
                } else {
                    Log.d("teste", "get failed with ", task.getException());
                }
            }
        });
        return this.usuarioRetornado;
    }

    @Override
    public FirebaseUser getFirebaseUser() {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        return user;
    }


}
