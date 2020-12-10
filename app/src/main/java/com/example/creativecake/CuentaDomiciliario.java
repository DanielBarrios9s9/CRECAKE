package com.example.creativecake;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CuentaDomiciliario extends Fragment {

    Button cerrar_sesion;
    DatabaseReference datos;
    TextView domicilios;
    Context globalContext;

    public CuentaDomiciliario() {
        // Required empty public constructor
    }

    public static CuentaDomiciliario newInstance(String param1, String param2) {
        CuentaDomiciliario fragment = new CuentaDomiciliario();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globalContext = this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cuenta_domiciliario, container, false);

        cerrar_sesion = (Button) v.findViewById(R.id.sesion);
        domicilios = (TextView) v.findViewById(R.id.domicilios);

        base();

        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
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

        return  v;
    }

    private void base() {

        datos = FirebaseDatabase.getInstance().getReference().child("entregasDomiciliario").child(SharedPreferences_Util.getPhone_SP(globalContext));
        datos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                domicilios.setText(snapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}