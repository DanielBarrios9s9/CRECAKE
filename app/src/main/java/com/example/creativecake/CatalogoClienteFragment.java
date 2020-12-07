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

        telefono = SharedPreferences_Util.getPhone_SP(globalContext);

        recyclerProductos = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos = new ArrayList<>();
        adaptador = new AdaptadorProductoCatalogo(listaProductos, globalContext, telefono);
        recyclerProductos.setAdapter(adaptador);
    }

    public void Base() {

        datosCatRef = FirebaseDatabase.getInstance().getReference().getRoot().child("productoTienda");
        datosCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds : snapshot.getChildren()) {
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