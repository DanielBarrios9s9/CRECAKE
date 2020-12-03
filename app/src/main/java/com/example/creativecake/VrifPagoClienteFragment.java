package com.example.creativecake;

import android.content.Context;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class VrifPagoClienteFragment extends Fragment {
    private Context globalContext = null;
    NavController navController;
    String telefono, item;
    DatabaseReference pago, domicilios, ventas, carrito;
    Boolean n, r;
    List<ItemHelperClass> listadoCompras;

    public VrifPagoClienteFragment() {
        // Required empty public constructor
    }

    public static VrifPagoClienteFragment newInstance(String param1, String param2) {
        VrifPagoClienteFragment fragment = new VrifPagoClienteFragment();
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
        return inflater.inflate(R.layout.fragment_vrif_pago_cliente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listadoCompras= new LinkedList<>();

        Intent intent1 = getActivity().getIntent();
        telefono = intent1.getStringExtra("telefono");

        navController= Navigation.findNavController(view);
        pago= FirebaseDatabase.getInstance().getReference().child("pagoCarrito").child(telefono);
        domicilios=FirebaseDatabase.getInstance().getReference().child("domicilios");
        ventas = FirebaseDatabase.getInstance().getReference().child("Ventas");
        carrito = FirebaseDatabase.getInstance().getReference().child("carrito").child(telefono);
        n=false;
        r=false;
        Query query = pago.limitToLast(1);

        while(true){
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    HelperValor compra = snapshot.getValue(HelperValor.class);
                    if(compra.getConfirmacion().equals("ACEPTADO")){
                        item=snapshot.getKey();
                        n=true;
                        r=false;
                        carrito.addListenerForSingleValueEvent(new ValueEventListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                listadoCompras.removeAll(listadoCompras);
                                for(DataSnapshot snap: snapshot.getChildren()){
                                    ItemHelperClass producto = snap.getValue(ItemHelperClass.class);
                                    producto.setFecha(LocalDate.now().toString());
                                    producto.setHora(LocalTime.now().toString());
                                    listadoCompras.add(producto);
                                }
                                domicilios.push().setValue(listadoCompras).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(globalContext, "Productos listos para asignar a domiciliario", Toast.LENGTH_SHORT).show();
                                        carrito.removeValue();
                                        carrito.child(telefono).child("1").setValue(" ").addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(globalContext, "Carrito listo para nueva compra", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(globalContext, "Error al vaciar el carrito", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(globalContext, "Error al agregar los productos a domicilios", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        int numItem = Integer.parseInt(item) + 1;
                        String newItem = String.valueOf(numItem);
                        pago.child(newItem).setValue(" ");
                    }
                    else if(compra.getConfirmacion().equals("DENEGADO")){
                        r=true;
                        n=true;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            if(n){ break;}
        }

        if(n && r){navController.navigate(R.id.denegadoClienteFragment);}
        else if (n && (!r)){navController.navigate(R.id.aceptadoClienteFragment);}
    }


}