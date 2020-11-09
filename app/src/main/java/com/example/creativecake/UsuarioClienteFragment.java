package com.example.creativecake;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


public class UsuarioClienteFragment extends Fragment {

    private TextView tvNombreGrande, etNombre, etCorreo, etPassword, etTelefono, etDireccion;
    private View UsuarioView;



    public UsuarioClienteFragment() {
        // Required empty public constructor
    }


    public View OnCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        UsuarioView = inflater.inflate(R.layout.fragment_usuario_cliente, container, false);

        tvNombreGrande = (TextView) UsuarioView.findViewById(R.id.nombre);
        etNombre = (TextView)UsuarioView.findViewById(R.id.idNombrePerfil);
        etDireccion = (TextView)UsuarioView.findViewById(R.id.idDireccionPerfil);
        etCorreo = (TextView)UsuarioView.findViewById(R.id.idCorreoPerfil);
        etPassword = (TextView)UsuarioView.findViewById(R.id.idPasswordPerfil);
        etTelefono = (TextView)UsuarioView.findViewById(R.id.idTelefonoPerfil);

        //funcion para mostrar toda la informacion del usuario en el activity
        mostrarInfo();
        return UsuarioView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void mostrarInfo()
    {
        Intent intent = getActivity().getIntent();
        String nombre = intent.getStringExtra("nombre");
        String password = intent.getStringExtra("password");
        String email = intent.getStringExtra("correo");
        String direccion = intent.getStringExtra("direccion");
        String telefono = intent.getStringExtra("telefono");

        tvNombreGrande.setText(nombre);
        etNombre.setText(nombre);
        etPassword.setText(password);
        etCorreo.setText(email);
        etDireccion.setText(direccion);
        etTelefono.setText(telefono);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_usuario_cliente, container, false);
    }
}