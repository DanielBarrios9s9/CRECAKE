package com.example.creativecake;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class OfertasClienteFragment extends Fragment {
    RecyclerView recyclerProductos;
    ArrayList<p_ejemplo_oferta> listaProductos;

    public OfertasClienteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_ofertas_cliente,container,false);
        listaProductos= new ArrayList<>();
        recyclerProductos = vista.findViewById(R.id.recyclerviewOfertas);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        llenarLista();
        AdaptadorProductoOferta adapter=new AdaptadorProductoOferta(listaProductos);
        recyclerProductos.setAdapter(adapter);
        return vista;
    }

    private void llenarLista() {
        //Hay que comenzar a llenar con los datos de la BD
    }
}