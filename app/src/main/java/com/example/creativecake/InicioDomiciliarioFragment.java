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

    Button btn1;
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


        recyclerProductos = (RecyclerView) v.findViewById(R.id.recyclerPedido);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaPedido = new ArrayList<>();
        adaptador = new AdaptadorPedido(listaPedido, fragmentManager);
        recyclerProductos.setAdapter(adaptador);

        Button cerrarsesion = v.findViewById(R.id.button10);

        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences_Util.savePhone_SP("", getActivity());
                SharedPreferences_Util.savePassword_SP("", getActivity());
                SharedPreferences_Util.saveType_SP("", getActivity());
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                requireActivity().finish();

            }
        });


        Base();

        return v;
    }

    public void Base() {

        datosCatRef = FirebaseDatabase.getInstance().getReference().getRoot().child("carrito");
        datosCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaPedido.removeAll(listaPedido);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    producto_carrito pedido = new producto_carrito();
                    pedido.setTelefono(ds.getKey());
                    pedido.setDir_usuario("Cra. 16a # 181c - 38");
                    pedido.setNom_usuario("Anderson Morales");
                    int count = 0;
                    for(DataSnapshot ds1: ds.getChildren()){
                        if(ds1.child("cantidad").getValue() != null) {
                            if (!ds1.child("cantidad").getValue().equals("")) {
                                count++;
                            }
                        }
                    }
                    String countR = String.valueOf(count);
                    pedido.setCantidad(countR);
                    listaPedido.add(pedido);

                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}