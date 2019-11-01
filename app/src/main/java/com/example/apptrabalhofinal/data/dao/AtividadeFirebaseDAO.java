package com.example.apptrabalhofinal.data.dao;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.apptrabalhofinal.data.model.Atividade;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AtividadeFirebaseDAO  implements  AtividadeDAO{

    private FirebaseAuth mAuth;
    private FirebaseFirestore database;

    static AtividadeFirebaseDAO atividadeFirebaseDAO;

    private AtividadeFirebaseDAO(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

    }

    public static AtividadeFirebaseDAO getInstance(){
        if(atividadeFirebaseDAO==null){
            atividadeFirebaseDAO = new AtividadeFirebaseDAO();
        }
        return atividadeFirebaseDAO;
    }

    @Override
    public ArrayList<Atividade> listarAtividadesTodos(String email) {

        return null;
    }

    @Override
    public ArrayList<Atividade> listarMinhasAtividades(String email) {
        return null;
    }

    @Override
    public ArrayList<Atividade> listarAtividadesParticipante(String email) {
        return null;
    }

    @Override
    public Atividade AtividadePorID(String id) {
        return null;
    }

    @Override
    public void addNovo(Atividade atividade) {
        database.collection("atividades")
                .add(atividade)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.i("teste","atividade add com sucesso "+ documentReference.getId());
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
        database.collection("atividade").document(id)
                .update("nome",atividade.getNome(),"descricao",atividade.getDescricao())
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

        return false;
    }

    @Override
    public void getAtividade(int id) {}
}
