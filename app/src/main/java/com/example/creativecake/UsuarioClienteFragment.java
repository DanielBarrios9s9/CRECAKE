package com.example.creativecake;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class UsuarioClienteFragment extends Fragment {

     TextView tvNombreGrande, etNombre, etCorreo, etPassword, etTelefono, etDireccion;
     View UsuarioView;
     String nombre, password, direccion, email, telefono;




    public UsuarioClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        nombre = getArguments().getString("nombre");
        password = getArguments().getString("password");
        direccion = getArguments().getString("direccion");
        email = getArguments().getString("correo");
        telefono = getArguments().getString("telefono");
        return inflater.inflate(R.layout.fragment_usuario_cliente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNombreGrande = (TextView) view.findViewById(R.id.idNombrePerfilGrande);
        etNombre = (TextView)view.findViewById(R.id.idNombrePerfil);
        etDireccion = (TextView)view.findViewById(R.id.idDireccionPerfil);
        etCorreo = (TextView)view.findViewById(R.id.idCorreoPerfil);
        etPassword = (TextView)view.findViewById(R.id.idPasswordPerfil);
        etTelefono = (TextView)view.findViewById(R.id.idTelefonoPerfil);

        /*tvNombreGrande.setText(nombre);
        etNombre.setText(nombre);
        etPassword.setText(password);
        etCorreo.setText(email);
        etDireccion.setText(direccion);
        etTelefono.setText(telefono);*/

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarioCliente").child("telefono");
        DataSnapshot snapshot;
    }

    /*private void isUser()
    {
        final String numeroIngresado = etTelefono.getText().toString().trim();
        final String passwordIngresado = etPassword.getText().toString().trim();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarioCliente");

        Query checkUsuario = reference.orderByChild("telefono").equalTo(numeroIngresado);

        checkUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    etTelefono.setError(null);

                    String passwordfromDB = snapshot.child(numeroIngresado).child("password").getValue(String.class);
                    String namefromDB = snapshot.child(numeroIngresado).child("nombre").getValue(String.class);
                    String addressfromDB = snapshot.child(numeroIngresado).child("direccion").getValue(String.class);
                    String emailfromDB = snapshot.child(numeroIngresado).child("correo").getValue(String.class);
                    String phonefromDB = snapshot.child(numeroIngresado).child("telefono").getValue(String.class);

                    tvNombreGrande.setText(namefromDB);
                    etNombre.setText(namefromDB);
                    etPassword.setText(passwordfromDB);
                    etCorreo.setText(emailfromDB);
                    etDireccion.setText(addressfromDB);
                    etTelefono.setText(phonefromDB);
                }else
                {
                    Toast.makeText(getContext(), "Error, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }*/
}