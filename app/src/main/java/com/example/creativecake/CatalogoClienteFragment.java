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
        listaProductos.add(new p_ejemplo_cat(R.mipmap.cupcake,"@string/cupcake_con_cobertura_de_fresa","@string/o_2000_c_u","@string/pas_l_Es",4.0));
        listaProductos.add(new p_ejemplo_cat(R.drawable.fr_crema,"@string/fresas_con_crema","@string/_5000_c_u","@string/pas_l_Es",4.0));
        listaProductos.add(new p_ejemplo_cat(R.drawable.p_gloria_h,"@string/pastel_gloria_de_bocadillo","@string/_1800_c_u","@string/pas_maria",5.0));
        listaProductos.add(new p_ejemplo_cat(R.drawable.p_leches,"@string/pastel_tres_leches_rosas","@string/_300000_c_u","@string/pas_sugar",4.3));
        listaProductos.add(new p_ejemplo_cat(R.drawable.p_mantequilla,"@string/pastel_tres_leches_cober_mantq","@string/_35000_c_u","@string/pasteler_a_santa_rosita",3.0));
    }
}