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
    ArrayList<p_ejemplo_tienda> listaProductos;

    public fragment_tienda_visualizar_producto() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_tienda_visualizar_producto,container,false);
        listaProductos= new ArrayList<>();
        recyclerProductos = vista.findViewById(R.id.recyclerTienda);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        llenarLista();
        AdaptadorProductoTienda adapter=new AdaptadorProductoTienda(listaProductos);
        recyclerProductos.setAdapter(adapter);
        return vista;
    }

    private void llenarLista() {
        //Hay que comenzar a llenar con los datos de la BD
        listaProductos.add(new p_ejemplo_tienda(R.mipmap.cupcake,"Cupcake de Fresa","$ 2.000 c/u","No Descuento"));
        listaProductos.add(new p_ejemplo_tienda(R.drawable.fr_crema,"Fresas con Crema","$ 5.000 c/u","-10%"));
        listaProductos.add(new p_ejemplo_tienda(R.drawable.p_gloria_h,"Pastel Gloria","$ 1.800 c/u","No descuento"));
        listaProductos.add(new p_ejemplo_tienda(R.drawable.p_leches,"Pastel Tres Leches","$ 30.000 c/u","-30%"));
        listaProductos.add(new p_ejemplo_tienda(R.drawable.p_mantequilla,"Pastel Mantequilla","$ 35.000 c/u","-50%"));
    }
}