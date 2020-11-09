package com.example.creativecake;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;
public class fragment_inventario_nuevoproducto extends Fragment {

    private EditText nombre;
    private EditText precio;
    private EditText descripcion;
    private EditText cantidad;
    private Button agregar;
    private Spinner tipo;
    private EditText oferta;

    public fragment_inventario_nuevoproducto() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventario_nuevoproducto, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        nombre = (EditText) view.findViewById(R.id.editNombrenuevoproducto);
        precio = (EditText) view.findViewById(R.id.editPrecionuevoproducto);
        descripcion = (EditText) view.findViewById(R.id.editDescripcionnuevoproducto);
        cantidad = (EditText) view.findViewById(R.id.editCantidad);
        tipo = (Spinner) view.findViewById(R.id.spinnerNuevoProducto);
        oferta = (EditText) view.findViewById(R.id.ofertaNuevaProducto);
        agregar = (Button) view.findViewById(R.id.buttonAgregarnuevoproducto);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.Tipo_producto, android.R.layout.simple_spinner_item);

        tipo.setAdapter(adapter);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.subirImagen1);

            }
        });
    }
}