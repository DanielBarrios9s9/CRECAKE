package com.example.creativecake;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InicioDomiciliarioFragment extends Fragment {

    boolean qBoton;
    AdaptadorPedido adaptador;
    private RecyclerView recyclerProductos;
    private ArrayList<producto_carrito> listaPedido;
    private DatabaseReference datosCatRef;
    FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getParentFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_inicio_domiciliario, container, false);

        qBoton = true;
        recyclerProductos = (RecyclerView) v.findViewById(R.id.recyclerPedido);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaPedido = new ArrayList<>();
        adaptador = new AdaptadorPedido(listaPedido, fragmentManager, qBoton);
        recyclerProductos.setAdapter(adaptador);

        Base();

        return v;
    }

    public void Base() {

        datosCatRef = FirebaseDatabase.getInstance().getReference().getRoot().child("domicilios");
        datosCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaPedido.removeAll(listaPedido);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    producto_carrito pedido = new producto_carrito();
                    if(ds.hasChild("0")) {
                        pedido.setKey(ds.getKey());
                        int count = 0;
                        for (DataSnapshot ds1 : ds.getChildren()) {
                            if (!ds1.getValue().equals(" ") && !ds1.getValue().equals(null)) {
                                if (pedido.getNom_usuario() == null || pedido.getNom_usuario().equals(" ")) {
                                    pedido.setTelefono(ds1.child("numeroUsuario").getValue().toString());
                                    pedido.setDir_usuario(ds1.child("direccionUsuario").getValue().toString());
                                    pedido.setNom_usuario(ds1.child("usuario").getValue().toString());
                                }
                                count++;

                            }
                        }

                        String countR = String.valueOf(count);
                        if(!countR.equals("0")) {
                            pedido.setCantidad(countR);
                            listaPedido.add(pedido);
                        }
                    }else{

                        pedido.setKey(ds.getKey());
                        pedido.setTelefono(ds.child("numeroUsuario").getValue().toString());
                        pedido.setDir_usuario(ds.child("direccionUsuario").getValue().toString());
                        pedido.setNom_usuario(ds.child("usuario").getValue().toString());

                        listaPedido.add(pedido);

                    }
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}