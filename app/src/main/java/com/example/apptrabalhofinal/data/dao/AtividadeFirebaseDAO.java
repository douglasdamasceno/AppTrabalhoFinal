package com.example.apptrabalhofinal.data.dao;


import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.apptrabalhofinal.data.model.Atividade;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.annotation.Nullable;

public class AtividadeFirebaseDAO  implements  AtividadeDAO{

    boolean removido;
    ArrayList<Atividade> listaTodasAtividades ;
    ArrayList<Atividade> listaAtividadesParticipo ;
    ArrayList<Atividade> minhasAtividades;
    Atividade atividadeRetornada;


    private FirebaseAuth mAuth;
    private FirebaseFirestore database;

    @Override
    public FirebaseFirestore getDatabase() {
        return database;
    }

    static AtividadeFirebaseDAO atividadeFirebaseDAO;

    private AtividadeFirebaseDAO(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
        minhasAtividades = new ArrayList<Atividade>();
        listaTodasAtividades = new ArrayList<Atividade>();
        listaAtividadesParticipo = new ArrayList<Atividade>();
        removido =false;
        atividadeRetornada = null;
    }

    public static AtividadeFirebaseDAO getInstance(){
        if(atividadeFirebaseDAO==null){
            atividadeFirebaseDAO = new AtividadeFirebaseDAO();
        }
        return atividadeFirebaseDAO;
    }

    @Override
    public ArrayList<Atividade> listarAtividadesTodos(final String email) {
        TodasAtividadeAsync todasAtividadeAsync = new TodasAtividadeAsync();
        todasAtividadeAsync.execute(email);
        ArrayList<Atividade> todasAtividades = new ArrayList<>();

        try {
            todasAtividades = todasAtividadeAsync.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return todasAtividades;
    }

    @Override
    public ArrayList<Atividade> listarMinhasAtividades(final String email) {
        MinhasAtividadeAsync atividadeAsync = new MinhasAtividadeAsync();
        atividadeAsync.execute(email);
        ArrayList<Atividade> novasAtividade = new ArrayList<>();
        try {
            novasAtividade = atividadeAsync.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return novasAtividade;
    }


    @Override
    public ArrayList<Atividade> listarAtividadesParticipante(String email) {
        database.collection("participantes")
                .whereArrayContains("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            listaAtividadesParticipo.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Atividade atividade = document.toObject(Atividade.class);
                                listaAtividadesParticipo.add(atividade);
                                Log.d("xxxx", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("minhas", "Error getting documents: ", task.getException());
                        }
                    }
                });
        return listaAtividadesParticipo;
    }

    @Override
    public Atividade AtividadePorID(String id) {
        database.collection("atividades")
                .whereEqualTo("id",id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                atividadeRetornada = document.toObject(Atividade.class);
                                //minhasAtividades.add(atividade);
                                Log.d("minhas", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("minhas", "Error getting documents: ", task.getException());
                        }
                    }
                });        Log.d("xxx", "Atividade retonada"+ atividadeRetornada);

        return  atividadeRetornada;
    }

    @Override
    public void addNovo(final Atividade atividade) {
        database.collection("atividades")
                .add(atividade)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        atividade.setId(documentReference.getId());
                        Log.i("teste","atividade add com sucesso "+ documentReference.getId());
                        editar(atividade.getId(),atividade);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("teste","falha ao add");
                    }
                });
    }

    @Override
    public void editar(String id, Atividade atividade) {
        database.collection("atividades").document(id)
                .update("id",atividade.getId(),"nome",atividade.getNome(),
                        "descricao",atividade.getDescricao(),"data",atividade.getData()
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                         Log.i("teste","editado com sucesso!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("teste","Falha ao editar: "+ e.getMessage());
                    }
                });
    }

    @Override
    public boolean remover(String id) {
        database.collection("atividades").document(id)
        .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("teste", "removedo com sucesso: "+task.getResult());
                        removido = true;
                    }
                });
        return true;
    }
    @Override
    public Atividade getAtividade(String id) {
        GetAtividadeAsync atividadeAsync = new GetAtividadeAsync();
        atividadeAsync.execute(id);
        Atividade atividade = null;
        try {
            atividade = atividadeAsync.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return  atividade;
    }
    @Override
    public void participarAtividade(String idUsuario,String email,String idAtividade) {
        Map<String,String> participar = new HashMap<>();
        participar.put("idUsuario",idUsuario);
        participar.put("email",email);
        participar.put("idAtividade",idAtividade);
        database.collection("participantes").add(participar)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("teste","participando com sucesso!");
                    }
                });
    }
    @Override
    public void naoParticiparAtividade(String idUsuario,String idAtividade) {
        final Map<String,String> participar = new HashMap<>();
        participar.put("idUsuario",idUsuario);
        participar.put("idAtividade",idAtividade);
        database.collection("participantes")
                .whereEqualTo("idUsuario", idUsuario)
                .whereEqualTo("idAtividade",idAtividade)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("teste", "Listen failed.", e);
                            return;
                        }

                        for (QueryDocumentSnapshot doc : value) {
                            if (doc.get("idUsuario") != null) {
                                DocumentSnapshot documentSnapshot = (DocumentSnapshot) doc.getData();
                                database.collection("participantes").document(documentSnapshot.getId())
                                        .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.i("teste","removido com sucesso");
                                    }
                                });
                            }
                        }
                        //Log.d(TAG, "Current cites in CA: " + cities);
                    }
                });
    }
    private class MinhasAtividadeAsync extends AsyncTask<String,Void,ArrayList<Atividade>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Atividade> doInBackground(String... strings) {
            final String email = strings[0];

            database.collection("atividades")
                    .whereEqualTo("emailProprietario", email)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                //                Log.w(TAG, "Listen failed.", e);
                                return;
                            }
                            minhasAtividades.clear();
                //            List<String> cities = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : value) {
                                Atividade atividade = doc.toObject(Atividade.class);
                                if (atividade.getEmailProprietario().equals(email)) {
                                    //cities.add(doc.getString("name"));
                                    minhasAtividades.add(atividade);
                                }
                            }
                            //Log.d(TAG, "Current cites in CA: " + cities);
                        }
                    });
            return minhasAtividades;
        }

        @Override
        protected void onPostExecute(ArrayList<Atividade> atividades) {
            super.onPostExecute(atividades);
        }

    }
    private class GetAtividadeAsync extends AsyncTask<String,Void,Atividade>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Atividade doInBackground(String... strings) {
            final String id = strings[0];

            DocumentReference docRef = database.collection("atividades").document(id);
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()) {
                        atividadeRetornada = documentSnapshot.toObject(Atividade.class);
                    }
                }
            });
            return  atividadeRetornada;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Atividade atividade) {
            super.onPostExecute(atividade);
        }
    }
    private class TodasAtividadeAsync extends AsyncTask<String,Void,ArrayList<Atividade>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<Atividade> doInBackground(String... strings) {
            final String email = strings[0];
               database.collection("atividades")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            listaTodasAtividades.clear();
                            for (DocumentSnapshot doc: task.getResult()) {
                                Atividade atividade = doc.toObject(Atividade.class);
                                Log.i("teste","Todas minhas atividade: "+  atividade.getNome());
                                if(!atividade.getEmailProprietario().equals(email)){
                                    listaTodasAtividades.add(atividade);
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.i("teste","falha na hora listar todas atividade: "+  e.getMessage());
                        }
                    });
            return listaTodasAtividades;
        }

        @Override
        protected void onPostExecute(ArrayList<Atividade> atividades) {
            super.onPostExecute(atividades);
        }

    }

}
