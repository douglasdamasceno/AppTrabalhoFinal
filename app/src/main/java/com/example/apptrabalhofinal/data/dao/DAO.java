package com.example.apptrabalhofinal.data.dao;

import java.util.ArrayList;

public interface DAO {
    ArrayList<Object> listarTodos();
    //void addNovo(Object);
    void remover(int id);
    void editar(int id);


}
