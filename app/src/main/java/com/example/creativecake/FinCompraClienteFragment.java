package com.example.creativecake;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.solver.widgets.Helper;
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

import java.util.Date;

public class FinCompraClienteFragment extends Fragment {
    NavController navController;
    private Context globalContext = null;
    TextView subT, des, com, tol;
    Button verificar;
    DatabaseReference pagoCarr, pagoC;
    String telefono, item;

    public FinCompraClienteFragment() {
        // Required empty public constructor
    }

    public static FinCompraClienteFragment newInstance(String param1, String param2) {
        FinCompraClienteFragment fragment = new FinCompraClienteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalContext = this.getActivity();
        if (getArguments() != null) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fin_compra_cliente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent1 = getActivity().getIntent();
        //telefono = intent1.getStringExtra("telefono"); null pointer error, telefono no tiene valor
        telefono = SharedPreferences_Util.getPhone_SP(globalContext);

        navController= Navigation.findNavController(view);

        subT= (TextView) view.findViewById(R.id.subtotal_compra);
        des = (TextView) view.findViewById(R.id.descuento_total_compra);
        com= (TextView) view.findViewById(R.id.comision_total_compra);
        tol= (TextView) view.findViewById(R.id.total_compra);
        verificar = (Button) view.findViewById(R.id.btn_verificar);

        Valores();
        Base();
    }

    public void Valores(){

        pagoC= FirebaseDatabase.getInstance().getReference("pagoCarrito").child(telefono);
        Query query = pagoC.limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    HelperValor valor_carrito = ds.getValue(HelperValor.class);
                    subT.setText(String.valueOf(valor_carrito.getSubtotal()));
                    des.setText(String.valueOf(valor_carrito.getDescuento()));
                    com.setText(String.valueOf(valor_carrito.getComision()));
                    tol.setText(String.valueOf(valor_carrito.getValor()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Base(){

        verificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagoCarr= FirebaseDatabase.getInstance().getReference("pagoCarrito").child(telefono);
                Query query = pagoCarr.limitToLast(1);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) { item = ds.getKey();}
                        pagoCarr.child(item).child("fecha").setValue(new Date().toString());
                        pagoCarr.child(item).child("confirmacion").setValue("pendiente").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(globalContext, "Estado de confirmación: pendiente", Toast.LENGTH_SHORT).show();
                               /* int numItem = Integer.parseInt(item) + 1;
                                String newItem = String.valueOf(numItem);
                                pagoCarr.child(newItem).child("fecha").setValue(" ");
                                pagoCarr.child(newItem).child("valor").setValue(" ");
                                pagoCarr.child(newItem).child("comision").setValue(" ");
                                pagoCarr.child(newItem).child("subtotal").setValue(" ");
                                pagoCarr.child(newItem).child("descuento").setValue(" ");
                                pagoCarr.child(newItem).child("confirmacion").setValue(" ");*/ //esto va en el boton fin junto con el vacio del carrito

                                navController.navigate(R.id.vrifPagoClienteFragment);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(globalContext, "Error al verificar el estado de confirmación", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}