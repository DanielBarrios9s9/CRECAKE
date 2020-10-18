package com.example.creativecake;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class CatalogoClienteFragment extends Fragment {
    RecyclerView recyclerProductos;
    ArrayList<p_ejemplo_cat> listaProductos;

    public CatalogoClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_catalogo_cliente,container,false);
        listaProductos= new ArrayList<>();
        recyclerProductos = vista.findViewById(R.id.recyclerview);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        llenarLista();
        AdaptadorProductos adapter=new AdaptadorProductos(listaProductos);
        recyclerProductos.setAdapter(adapter);
        return vista;
    }

    private void llenarLista() {
        //Hay que comenzar a llenar con los datos de la BD
        listaProductos.add(new p_ejemplo_cat(R.mipmap.cupcake,"Cupcake de Fresa","$ 2.000 c/u","Pastelería La Española",4.0));
        listaProductos.add(new p_ejemplo_cat(R.drawable.fr_crema,"Fresas con Crema","$ 5.000 c/u","Pastelería La Española",4.0));
        listaProductos.add(new p_ejemplo_cat(R.drawable.p_gloria_h,"Pastel Gloria","$ 1.800 c/u","Pastelería La Española",5.0));
        listaProductos.add(new p_ejemplo_cat(R.drawable.p_leches,"Pastel Tres Leches","$ 30.000 c/u","Pastelería La Española",4.3));
        listaProductos.add(new p_ejemplo_cat(R.drawable.p_mantequilla,"Pastel Mantequilla","$ 35.000 c/u","Pastelería La Española",3.0));
    }
}