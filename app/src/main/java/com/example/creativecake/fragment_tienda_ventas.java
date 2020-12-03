package com.example.creativecake;

import android.content.Context;
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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class fragment_tienda_ventas extends Fragment {
    View v;
    AdaptadorPedidosTienda adaptador;
    private RecyclerView recyclerProductos;
    private ArrayList<PedidoTienda> listaProductos;
    private DatabaseReference datosPedidos;
    String telefono;
    private Context globalContext = null;

    public fragment_tienda_ventas() {
        // Required empty public constructor
    }

    public static fragment_tienda_ventas newInstance(String param1, String param2) {
        fragment_tienda_ventas fragment = new fragment_tienda_ventas();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tienda_ventas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v=view;
        Inicializar();
        Base();
    }

    private void Inicializar() {
        telefono = SharedPreferences_Util.getPhone_SP(globalContext);

        recyclerProductos = (RecyclerView) v.findViewById(R.id.recyclerPedidos);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos = new ArrayList<>();
        adaptador = new AdaptadorPedidosTienda(listaProductos, globalContext, telefono);
        recyclerProductos.setAdapter(adaptador);
    }

    private void Base(){
        try{
            datosPedidos = FirebaseDatabase.getInstance().getReference().getRoot().child("Ventas").child(telefono);
            datosPedidos.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listaProductos.removeAll(listaProductos);
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        PedidoTienda producto = ds.getValue(PedidoTienda.class);
                        listaProductos.add(producto);
                    }
                    adaptador.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }catch (Exception e){
            datosPedidos = FirebaseDatabase.getInstance().getReference().getRoot().child("Ventas").child(telefono);
            datosPedidos.setValue(" ");
            Toast.makeText(globalContext, "No tienes pedidos pendientes por entregar", Toast.LENGTH_SHORT).show();
        }


    }
}