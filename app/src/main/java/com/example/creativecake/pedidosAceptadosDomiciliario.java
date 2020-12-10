package com.example.creativecake;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class pedidosAceptadosDomiciliario extends Fragment {

    Button ruta, ver_dirs;
    TextView cant;
    boolean qBoton;
    int count;

    AdaptadorPedido adaptador;
    private RecyclerView recyclerProductos;
    private ArrayList<producto_carrito> listaPedido;
    private DatabaseReference datosCatRef;
    FragmentManager fragmentManager;
    private Context globalContext = null;

    public pedidosAceptadosDomiciliario() {
        // Required empty public constructor
    }

    public static pedidosAceptadosDomiciliario newInstance(String param1, String param2) {
        pedidosAceptadosDomiciliario fragment = new pedidosAceptadosDomiciliario();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentManager = getParentFragmentManager();
        globalContext = this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pedidos_aceptados_domiciliario, container, false);


        qBoton = false;
        ver_dirs = (Button) v.findViewById(R.id.btn_direcciones);
        ruta = (Button) v.findViewById(R.id.btn_ruta);
        cant = (TextView) v.findViewById(R.id.cant);
        recyclerProductos = (RecyclerView) v.findViewById(R.id.recyclerPedido);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaPedido = new ArrayList<>();

        Base();

        System.out.println("LLLLLLLLLLLLiiiiiiiiiiiiiiiissssstaaaa: " + listaPedido);

        FragmentActivity activity = getActivity();
        adaptador = new AdaptadorPedido(listaPedido, fragmentManager, qBoton, activity, globalContext, SharedPreferences_Util.getPhone_SP(globalContext));
        recyclerProductos.setAdapter(adaptador);

        ruta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String url = "https://waze.com/ul";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);

                } catch (ActivityNotFoundException ex) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                    startActivity(intent);

                }

            }
        });

        ver_dirs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_direcciones direcciones = new Dialog_direcciones(globalContext, SharedPreferences_Util.getPhone_SP(globalContext));
            }
        });

        return v;
    }

    public void Base() {

        datosCatRef = FirebaseDatabase.getInstance().getReference().getRoot().child("pedidoAgregadoDomiciliario").child(SharedPreferences_Util.getPhone_SP(globalContext));
        datosCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                count = 0;
                listaPedido.removeAll(listaPedido);
                for (DataSnapshot ds : snapshot.getChildren()) {
                    producto_carrito pedido = new producto_carrito();
                    if(ds.hasChild("0")) {
                        pedido.setKey(ds.getKey());
                        int con = 0;
                        for (DataSnapshot ds1 : ds.getChildren()) {
                            if (!ds1.getValue().equals(" ") && !ds1.getValue().equals(null)) {
                                if (pedido.getNom_usuario() == null || pedido.getNom_usuario().equals(" ")) {
                                    pedido.setTelefono(ds1.child("numeroUsuario").getValue().toString());
                                    pedido.setDir_usuario(ds1.child("direccionUsuario").getValue().toString());
                                    pedido.setNom_usuario(ds1.child("usuario").getValue().toString());
                                }
                                con++;
                            }
                        }
                        String countR = String.valueOf(con);
                        if (!countR.equals("0")) {
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
                    count++;
                }
                if(!String.valueOf(count).equals("0")) {
                    ruta.setEnabled(true);
                    ver_dirs.setEnabled(true);
                }else{
                    ruta.setEnabled(false);
                    ver_dirs.setEnabled(false);
                }
                cant.setText(String.valueOf(count));
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}