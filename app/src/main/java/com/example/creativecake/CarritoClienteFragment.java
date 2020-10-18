package com.example.creativecake;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CarritoClienteFragment extends Fragment {
    RecyclerView recyclerProductos;
    ArrayList<p_ejemplo_carrito> listaProductos;

    public CarritoClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_carrito_cliente,container,false);
        listaProductos= new ArrayList<>();
        recyclerProductos = vista.findViewById(R.id.recyclerviewCarrito);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        llenarLista();
        AdaptadorProductosCarrito adapter=new AdaptadorProductosCarrito(listaProductos);
        recyclerProductos.setAdapter(adapter);
        return vista;
    }

    private void llenarLista() {
        //Hay que comenzar a llenar con los datos de la BD
        listaProductos.add(new p_ejemplo_carrito(R.drawable.fr_crema,"Fresas con Crema","5.000","1"));
        listaProductos.add(new p_ejemplo_carrito(R.drawable.p_mantequilla,"Pastel Mantequilla","35.000","1"));
    }
}