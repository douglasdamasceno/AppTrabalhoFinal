package com.example.apptrabalhofinal.data.dao;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apptrabalhofinal.data.model.Atividade;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;
import java.util.concurrent.ExecutionException;


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
    public void addNovo(final String fotoPerfil, final String username, final String email, final String senha) {
        mAuth.createUserWithEmailAndPassword(email,senha)
            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Log.i("teste","sucesso id do usuriao: "+ authResult.getUser().getUid());
                    String id = authResult.getUser().getUid();
                    salvaInformacaoUsuario(fotoPerfil,id,username,email,senha);
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
                        Log.i("tags","salvo no db" + documentReference.getId());
                        atualizarID(documentReference.getId());
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
    public void atualizarID(final String id) {
        DocumentReference docRef = database.collection("user").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    Log.d("tagc", "DocumentSnapshot data: " + document.getData());

                    if (document.exists()) {
                        usuarioRetornado = document.toObject(Usuario.class);
                        usuarioRetornado.setId(id);

                        Log.d("tagc", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("tagc", "No such document");
                    }
                } else {
                    Log.d("tagc", "get failed with ", task.getException());
                }
            }
        });
    }

        @Override
    public void editar(String id,String email, String username, String senha, String idade, String sexo) {

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
    public Usuario getUsuarioPorEmail(final String email) {
        database.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                usuarioRetornado = document.toObject(Usuario.class);
                                if(email.equals(usuarioRetornado.getMeuPerfil().getEmail())){
                                    return;
                                }
                                Log.d("novo", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("novo", "Error getting documents.", task.getException());
                        }
                    }
                });
     //   Log.d("novo", "usuario r "+ usuarioRetornado.toString());
        return usuarioRetornado;
    }


    @Override
    public Usuario getUserPorID(final String id) {
        GetUsuarioAsycn getUsuarioAsycn = new GetUsuarioAsycn();
        getUsuarioAsycn.execute(id);
        Usuario usuario = null;
        try {
            usuario = getUsuarioAsycn.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return usuario;
    }

    @Override
    public FirebaseUser getFirebaseUser() {
        this.user = FirebaseAuth.getInstance().getCurrentUser();
        return user;
    }

    private class GetUsuarioAsycn extends AsyncTask<String,Void,Usuario>{

        @Override
        protected Usuario doInBackground(String... strings) {
            final String id = strings[0];
            database.collection("user")
                    .whereEqualTo("id", id)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d("novo", document.getId() + " => " + document.getData());
                                    usuarioRetornado = document.toObject(Usuario.class);

                                    Log.d("novo", "usuario r "+ usuarioRetornado.toString());
                                }
                            } else {
                                Log.d("novo", "Error getting documents: ", task.getException());
                            }
                        }
                    });
            Log.d("novo", "usuario r "+ usuarioRetornado.toString());
            return usuarioRetornado;
        }
    }
}
