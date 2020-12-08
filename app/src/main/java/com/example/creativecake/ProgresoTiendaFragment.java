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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProgresoTiendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProgresoTiendaFragment extends Fragment {
    View v;
    AdapCotizaciones adaptador;
    private RecyclerView recyclerProductos;
    private ArrayList<CotizacionHelperClass> listaProductos;
    private DatabaseReference datosCotizaciones;
    String telefono;
    private Context globalContext = null;


    public ProgresoTiendaFragment() {
        // Required empty public constructor
    }

    public static ProgresoTiendaFragment newInstance(String param1, String param2) {
        ProgresoTiendaFragment fragment = new ProgresoTiendaFragment();
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
        return inflater.inflate(R.layout.fragment_progreso_tienda, container, false);
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

        recyclerProductos = (RecyclerView) v.findViewById(R.id.recyclerProgreso);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos = new ArrayList<>();
        adaptador = new AdapCotizaciones(listaProductos, globalContext, telefono);
        recyclerProductos.setAdapter(adaptador);
    }

    public void Base() {

        datosCotizaciones = FirebaseDatabase.getInstance().getReference().getRoot().child("cotiTienda").child(telefono);
        datosCotizaciones.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    CotizacionHelperClass producto = ds.getValue(CotizacionHelperClass.class);
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