package com.example.creativecake;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class fragment_tienda_visualizar_producto extends Fragment {
    RecyclerView recyclerProductos;
    private Context globalContext = null;
    View v;
    StoreHelperClass tienda;
    String telefono;
    AdaptadorProductoTienda adaptador;
    private ArrayList<producto_ejemplo> listaProductos;
    private DatabaseReference datosStoreRef, datosUsuario;

    public fragment_tienda_visualizar_producto() {
        // Required empty public constructor
    }

    public static fragment_tienda_visualizar_producto newInstance(String param1, String param2) {
        fragment_tienda_visualizar_producto fragment = new fragment_tienda_visualizar_producto();
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
        View vista=inflater.inflate(R.layout.fragment_tienda_visualizar_producto,container,false);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v=view;
        Inicializar();
        Base();
    }

    private void Inicializar() {
        recyclerProductos = v.findViewById(R.id.recyclerTienda);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos = new ArrayList<>();
        adaptador = new AdaptadorProductoTienda(listaProductos, globalContext);
        recyclerProductos.setAdapter(adaptador);
    }

    private void Base() {
        telefono = SharedPreferences_Util.getPhone_SP(globalContext);
        datosUsuario= FirebaseDatabase.getInstance().getReference("usuarioNegocio").child(telefono);
        datosUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tienda =snapshot.getValue(StoreHelperClass.class);
                try {
                    System.out.println(tienda.getNombre());
                    Buscar();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void Buscar(){
        datosStoreRef= FirebaseDatabase.getInstance().getReference("productoTienda");
        Query productosTienda = datosStoreRef.orderByChild("user_name").equalTo(tienda.getNombre());
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