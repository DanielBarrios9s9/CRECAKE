package com.example.creativecake;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class fragment_tienda_visualizar_producto extends Fragment {
    RecyclerView recyclerProductos;

    public fragment_tienda_visualizar_producto() {
        // Required empty public constructor
    }

    //Hay que crear una verificación de USER para desplegar solo los productos de diche pastelería

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_tienda_visualizar_producto,container,false);
        recyclerProductos = vista.findViewById(R.id.recyclerTienda);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        return vista;
    }
}