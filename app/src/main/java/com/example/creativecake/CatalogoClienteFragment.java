package com.example.creativecake;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CatalogoClienteFragment extends Fragment {
    View v;
    AdaptadorProductoCatalogo adaptador;
    private RecyclerView recyclerProductos;
    private ArrayList<producto_ejemplo> listaProductos;
    private DatabaseReference datosCatRef;
    CheckBox tipoTorta, tipoPostre, tipoHojaldre, tipoOtro;
    EditText precio;

    public CatalogoClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_catalogo_cliente,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v=view;
        Inicializar();
        Base();
    }

    public void Inicializar(){
        recyclerProductos = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos = new ArrayList<>();
        adaptador = new AdaptadorProductoCatalogo(listaProductos);
        recyclerProductos.setAdapter(adaptador);

        tipoTorta = (CheckBox) v.findViewById(R.id.checkTorta);
        tipoPostre = (CheckBox) v.findViewById(R.id.checkPostre);
        tipoHojaldre = (CheckBox) v.findViewById(R.id.checkHojaldre);
        tipoOtro = (CheckBox) v.findViewById(R.id.checkOtro);
        precio= (TextInputEditText) v.findViewById(R.id.Precio_maximo);
    }

    public void Base(){
        datosCatRef= FirebaseDatabase.getInstance().getReference().getRoot().child("productoTienda");
        datosCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int precioMax = 0;
                listaProductos.removeAll(listaProductos);
                try {
                    precioMax= Integer.parseInt(precio.getText().toString());
                } catch (Exception e) {
                    precioMax = 0;
                }
                for (DataSnapshot ds: snapshot.getChildren()) {
                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                    listaProductos.add(producto);
                    //if ((ds.child("oferta").getValue().toString() == " ") && (ds.child("oferta").getValue() != null)){

                        /*if(precioMax != 0){
                            if (Integer.parseInt(ds.child("precio").getValue().toString()) <= precioMax) {
                                if (tipoTorta.isChecked()) {
                                    if (ds.child("tipo").getValue().toString() == "Torta") {
                                        producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                                        listaProductos.add(producto);
                                    }
                                }
                                else if (tipoPostre.isChecked()){
                                    if (snapshot.child("tipo").getValue().toString() == "Postre"){
                                        producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                                        listaProductos.add(producto);
                                    }
                                }
                                else if (tipoHojaldre.isChecked()){
                                    if (snapshot.child("tipo").getValue().toString() == "Hojaldre"){
                                        producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                                        listaProductos.add(producto);
                                    }
                                }
                                else if (tipoOtro.isChecked()){
                                    if (snapshot.child("tipo").getValue().toString() == "Otro"){
                                        producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                                        listaProductos.add(producto);
                                    }
                                }
                                else{
                                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                                    listaProductos.add(producto);
                                }
                            }
                        }
                        else{
                            if (tipoTorta.isChecked()) {
                                if (ds.child("tipo").getValue().toString() == "Torta") {
                                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                                    listaProductos.add(producto);
                                }
                            }
                            else if (tipoPostre.isChecked()){
                                if (snapshot.child("tipo").getValue().toString() == "Postre"){
                                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                                    listaProductos.add(producto);
                                }
                            }
                            else if (tipoHojaldre.isChecked()){
                                if (snapshot.child("tipo").getValue().toString() == "Hojaldre"){
                                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                                    listaProductos.add(producto);
                                }
                            }
                            else if (tipoOtro.isChecked()){
                                if (snapshot.child("tipo").getValue().toString() == "Otro"){
                                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                                    listaProductos.add(producto);
                                }
                            }
                            else{
                                producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                                listaProductos.add(producto);
                            }
                        }
                    }*/
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}