package com.example.creativecake;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UsuarioClienteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuarioClienteFragment extends Fragment {

    private TextView tvNombreGrande, etNombre, etCorreo, etPassword, etTelefono, etDireccion;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UsuarioClienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsuarioClienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UsuarioClienteFragment newInstance(String param1, String param2) {
        UsuarioClienteFragment fragment = new UsuarioClienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            tvNombreGrande = (TextView) getActivity().findViewById(R.id.nombre);
            etNombre = (TextView)getActivity().findViewById(R.id.idNombrePerfil);
            etDireccion = (TextView)getActivity().findViewById(R.id.idDireccionPerfil);
            etCorreo = (TextView)getActivity().findViewById(R.id.idCorreoPerfil);
            etPassword = (TextView)getActivity().findViewById(R.id.idPasswordPerfil);
            etTelefono = (TextView)getActivity().findViewById(R.id.idTelefonoPerfil);

            //funcion para mostrar toda la informacion del usuario en el activity
            mostrarInfo();
        }
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