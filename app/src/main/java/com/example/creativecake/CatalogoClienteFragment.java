package com.example.creativecake;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StableIdKeyProvider;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CatalogoClienteFragment extends Fragment {
    View v;
    AdaptadorProductoCatalogo adaptador;
    private RecyclerView recyclerProductos;
    private ArrayList<producto_ejemplo> listaProductos;
    private DatabaseReference datosCatRef;
    CheckBox tipoTorta, tipoPostre, tipoHojaldre, tipoOtro, oferta;
    EditText precio;
    Button botonFiltrar;
    String telefono;
    private Context globalContext = null;

    public CatalogoClienteFragment() {
        // Required empty public constructor
    }

    public static CatalogoClienteFragment newInstance(String param1, String param2) {
        CatalogoClienteFragment fragment = new CatalogoClienteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
        globalContext = this.getActivity();
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

        Intent intent1 = getActivity().getIntent();
        telefono = intent1.getStringExtra("telefono");
        System.out.println(telefono);
        recyclerProductos = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos = new ArrayList<>();
        adaptador = new AdaptadorProductoCatalogo(listaProductos, globalContext, telefono);
        recyclerProductos.setAdapter(adaptador);

        botonFiltrar= v.findViewById(R.id.bRefrescar);
        oferta = (CheckBox) v.findViewById(R.id.checkOferta);
        tipoTorta = (CheckBox) v.findViewById(R.id.checkTorta);
        tipoPostre = (CheckBox) v.findViewById(R.id.checkPostre);
        tipoHojaldre = (CheckBox) v.findViewById(R.id.checkHojaldre);
        tipoOtro = (CheckBox) v.findViewById(R.id.checkOtro);
        precio= (EditText) v.findViewById(R.id.Precio_Maximo);

        botonFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaProductos.clear();

                if (tipoTorta.isChecked()) {
                    BaseTorta();
                }
                else if(tipoPostre.isChecked()){
                    BasePostre();
                }
                else if(tipoHojaldre.isChecked()){
                    BaseHojaldre();
                }
                else if(tipoOtro.isChecked()){
                    BaseOtro();
                }
                else if(oferta.isChecked()){
                    BaseOferta();
                }
                /*else if ((precio.getText().toString() != " ") || (precio.getText().toString() != "")){
                    BasePrecio();
                }*/
                else{
                    Base();
                }
            }
        });
    }

    public void Base(){

        datosCatRef= FirebaseDatabase.getInstance().getReference().getRoot().child("productoTienda");
        datosCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                    listaProductos.add(producto);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void BaseOferta(){ //Arreglar

        datosCatRef= FirebaseDatabase.getInstance().getReference("productoTienda");
        Query productosTienda = datosCatRef.orderByChild("oferta").equalTo("50");
        productosTienda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                    listaProductos.add(producto);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void BasePostre(){
        datosCatRef= FirebaseDatabase.getInstance().getReference("productoTienda");
        Query productosTienda = datosCatRef.orderByChild("tipo").equalTo("Postre");
        productosTienda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                    listaProductos.add(producto);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void BaseTorta(){
        datosCatRef= FirebaseDatabase.getInstance().getReference("productoTienda");
        Query productosTienda = datosCatRef.orderByChild("tipo").equalTo("Torta");
        productosTienda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                    listaProductos.add(producto);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void BaseHojaldre(){
        datosCatRef= FirebaseDatabase.getInstance().getReference("productoTienda");
        Query productosTienda = datosCatRef.orderByChild("tipo").equalTo("Hojaldre");
        productosTienda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                    listaProductos.add(producto);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void BaseOtro(){
        datosCatRef= FirebaseDatabase.getInstance().getReference("productoTienda");
        Query productosTienda = datosCatRef.orderByChild("tipo").equalTo("Otro");
        productosTienda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                    listaProductos.add(producto);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void BasePrecio(){ //Arreglar
        datosCatRef= FirebaseDatabase.getInstance().getReference("productoTienda");
        Query productosTienda = datosCatRef.orderByChild("precio").equalTo("5000");
        productosTienda.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                    listaProductos.add(producto);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}