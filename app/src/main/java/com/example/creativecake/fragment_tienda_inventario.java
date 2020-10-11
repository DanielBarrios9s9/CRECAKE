package com.example.creativecake;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
public class fragment_tienda_inventario extends Fragment {

    public fragment_tienda_inventario() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tienda_inventario, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        Button botonVisualizar = view.findViewById(R.id.botonVisualizar);
        Button botonAgregar = view.findViewById(R.id.botonAgregar);
        Button botonEliminar = view.findViewById(R.id.botonEliminar);
        Button botonEditar = view.findViewById(R.id.botonEditar);
        Button botonModCantidad = view.findViewById(R.id.botonModCantidad);

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.fragment_inventario_nuevoproducto);
            }
        });

        botonVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.fragment_tienda_visualizar_producto);
            }
        });

        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.fragment_tienda_visualizar_producto);
            }
        });

        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.fragment_tienda_visualizar_producto);
            }
        });
    }
}