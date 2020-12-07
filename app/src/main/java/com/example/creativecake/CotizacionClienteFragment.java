package com.example.creativecake;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CotizacionClienteFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference reference, datosUsuario;
    UserHelperClass usuario;

    EditText nombre_cotizacion, precio, fecha, hora, especificaciones;
    Spinner tamaño;
    Spinner cobertura;
    Spinner sabor;
    Spinner decoración;
    String telefono;
    private Context globalContext = null;
    View vista;

    public CotizacionClienteFragment() {
        // Required empty public constructor
    }

    public static CotizacionClienteFragment newInstance(String param1, String param2) {
        CotizacionClienteFragment fragment = new CotizacionClienteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        globalContext = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista = inflater.inflate(R.layout.fragment_cotizacion_cliente, container, false);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        telefono = SharedPreferences_Util.getPhone_SP(globalContext);

        Button boton_enviar_coti= vista.findViewById(R.id.btn_enviar);
        Button boton_revisar_coti= vista.findViewById(R.id.btn_revisar);
        ImageButton boton_bot= vista.findViewById(R.id.btn_bot);

        nombre_cotizacion = (EditText) vista.findViewById(R.id.editNombreCotizacion);
        decoración = (Spinner) vista.findViewById(R.id.decoracion);
        sabor = (Spinner) vista.findViewById(R.id.sabor);
        cobertura = (Spinner) vista.findViewById(R.id.cobertura);
        tamaño = (Spinner) vista.findViewById(R.id.tamaño);
        precio = (EditText) vista.findViewById(R.id.editPrecio);
        fecha = (EditText) vista.findViewById(R.id.editFecha);
        hora = (EditText) vista.findViewById(R.id.editHora);
        especificaciones = (EditText) vista.findViewById(R.id.editTextTextMultiLine);


        final NavController navController= Navigation.findNavController(view);

        boton_enviar_coti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                datosUsuario= FirebaseDatabase.getInstance().getReference("usuarioCliente").child(telefono);
                datosUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String direccionFROMDB = snapshot.child("direccion").getValue(String.class);
                        String telefonoFROMDB = snapshot.child("telefono").getValue(String.class);
                        String nombreFROMDB = snapshot.child("nombre").getValue(String.class);

                        String nombre_ = nombre_cotizacion.getText().toString();
                        String decoracion_ = decoración.getSelectedItem().toString();
                        String sabor_ = sabor.getSelectedItem().toString();
                        String cobertura_ = cobertura.getSelectedItem().toString();
                        String tamaño_ = tamaño.getSelectedItem().toString();
                        String precio_ = precio.getText().toString();
                        String fecha_ = fecha.getText().toString();
                        String hora_ = hora.getText().toString();
                        String especificaciones_ = especificaciones.getText().toString();

                        database = FirebaseDatabase.getInstance();
                        reference = database.getReference("cotizaciones");

                        final CotizacionHelperClass helperClass = new CotizacionHelperClass(nombre_,
                                decoracion_, sabor_, cobertura_, tamaño_, precio_, fecha_, hora_,direccionFROMDB
                                , telefonoFROMDB, nombreFROMDB,
                                especificaciones_,"PENDIENTE");

                        reference.child(telefono).setValue(helperClass);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Toast.makeText(getActivity(), "Cotización enviada", Toast.LENGTH_SHORT).show();
            }
        });

        boton_revisar_coti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.revisarCotiClienteFragment);
            }
        });

        boton_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navController.navigate(R.id.cotizacionClienteFragment); abrir el bot
            }
        });

        tamaño= (Spinner)vista.findViewById(R.id.tamaño);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(getContext(), R.array.tamaño, android.R.layout.simple_spinner_item);
        tamaño.setAdapter(adapter1);

        cobertura= (Spinner)vista.findViewById(R.id.cobertura);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getContext(), R.array.cobertura, android.R.layout.simple_spinner_item);
        cobertura.setAdapter(adapter2);

        sabor= (Spinner)vista.findViewById(R.id.sabor);
        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(getContext(), R.array.sabor, android.R.layout.simple_spinner_item);
        sabor.setAdapter(adapter3);

        decoración= (Spinner)vista.findViewById(R.id.decoracion);
        ArrayAdapter<CharSequence> adapter4=ArrayAdapter.createFromResource(getContext(), R.array.decoracion, android.R.layout.simple_spinner_item);
        decoración.setAdapter(adapter4);
    }
}