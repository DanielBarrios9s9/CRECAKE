package com.example.creativecake;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class VerCarritoDomiciliario extends Fragment {

    View v;
    TextView nombre_user;
    Button agregar_carrito;
    AdaptadorCarritoDomiciliario adapterDomiciliario;
    private RecyclerView recycler_pedidos;
    private ArrayList<Pedido> listaDel_carrito;
    private DatabaseReference pedidosCarrito;
    private Context globalContext = null;

    public VerCarritoDomiciliario() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalContext = this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ver_carrito_domiciliario, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.v =view;

        inicializar();
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                nombre_user.setText(result.getString("nom_user"));

                pedidosCarrito= FirebaseDatabase.getInstance().getReference().getRoot().child("carrito").child(result.getString("telefono"));
                pedidosCarrito.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listaDel_carrito.removeAll(listaDel_carrito);
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            if(!ds.child("imagen").getValue().equals(" ")) {
                                Pedido producto = ds.getValue(Pedido.class);
                                listaDel_carrito.add(producto);
                            }
                        }
                        adapterDomiciliario.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void inicializar() {

        nombre_user = (TextView) v.findViewById(R.id.nom_user);
        recycler_pedidos = (RecyclerView) v.findViewById(R.id.recyclerPedido);
        recycler_pedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaDel_carrito = new ArrayList<>();
        adapterDomiciliario = new AdaptadorCarritoDomiciliario(listaDel_carrito, globalContext);
        recycler_pedidos.setAdapter(adapterDomiciliario);
    }
}