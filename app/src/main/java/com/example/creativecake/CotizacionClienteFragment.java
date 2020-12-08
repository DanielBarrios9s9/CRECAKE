package com.example.creativecake;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class CotizacionClienteFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference reference, datosUsuario;
    UserHelperClass usuario;
    Boolean n;

    EditText nombre_cotizacion, precio, especificaciones;
    Button fecha, hora;
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

        nombre_cotizacion = (EditText) vista.findViewById(R.id.editNombreCotizacion);
        decoración = (Spinner) vista.findViewById(R.id.decoracion);
        sabor = (Spinner) vista.findViewById(R.id.sabor);
        cobertura = (Spinner) vista.findViewById(R.id.cobertura);
        tamaño = (Spinner) vista.findViewById(R.id.tamaño);
        precio = (EditText) vista.findViewById(R.id.editPrecio);
        fecha = (Button) vista.findViewById(R.id.editFecha);
        hora = (Button) vista.findViewById(R.id.editHora);
        especificaciones = (EditText) vista.findViewById(R.id.editTextTextMultiLine);

        fecha.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int dia = c.get(Calendar.DAY_OF_MONTH);
                int mes = c.get(Calendar.MONTH);
                int año = c.get(Calendar.YEAR);

                DatePickerDialog calendario = new DatePickerDialog(globalContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        fecha.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                }, dia, mes, año);

                calendario.show();
            }
        });

        hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int horaS = c.get(Calendar.HOUR_OF_DAY);
                int minutos = c.get(Calendar.MINUTE);

                TimePickerDialog tiempo = new TimePickerDialog(globalContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hora.setText(hourOfDay+":"+minute);
                    }
                }, horaS, minutos, true);

                tiempo.show();
            }
        });


        final NavController navController= Navigation.findNavController(view);

        boton_enviar_coti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datosUser();
            }
        });


        boton_revisar_coti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.revisarCotiClienteFragment);
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

    public void datosUser(){
        n=false;
        datosUsuario= FirebaseDatabase.getInstance().getReference("usuarioCliente").child(telefono);
        datosUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usuario =snapshot.getValue(UserHelperClass.class);
                try {
                    System.out.println(usuario.getNombre());
                    n=true;
                    subirCoti();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void subirCoti(){
        final String nombre_ = nombre_cotizacion.getText().toString();
        final String decoracion_ = decoración.getSelectedItem().toString();
        final String sabor_ = sabor.getSelectedItem().toString();
        final String cobertura_ = cobertura.getSelectedItem().toString();
        final String tamaño_ = tamaño.getSelectedItem().toString();
        final String precio_ = precio.getText().toString();
        final String fecha_ = fecha.getText().toString();
        final String hora_ = hora.getText().toString();
        final String especificaciones_ = especificaciones.getText().toString();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("cotizaciones").child(telefono);
        System.out.println(telefono);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.getValue().equals(" ")){
                    System.out.println("entra a if");
                    CotizacionHelperClass coti = snapshot.getValue(CotizacionHelperClass.class);
                    if(coti.getEstadoPago().equals("PENDIENTE")){
                        Toast.makeText(getActivity(), "Tienes una cotización pendiente", Toast.LENGTH_SHORT).show();
                    }
                    else if (coti.getEstadoPago().equals("EN PROGRESO")){
                        Toast.makeText(getActivity(), "Una repostería a aceptado tu pedido. ¡Revísalao!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        final CotizacionHelperClass helperClass = new CotizacionHelperClass(nombre_,
                                decoracion_, sabor_, cobertura_, tamaño_, precio_, fecha_, hora_,usuario.getDireccion()
                                , usuario.getTelefono(), usuario.getNombre(),
                                especificaciones_,"PENDIENTE");

                        reference.setValue(helperClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                nombre_cotizacion.setText("");
                                precio.setText("");
                                fecha.setText("");
                                hora.setText("");
                                especificaciones.setText("");
                                Toast.makeText(getActivity(), "Cotización enviada", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{
                    System.out.println("entra a else");
                    CotizacionHelperClass helperClass = new CotizacionHelperClass(nombre_,
                            decoracion_, sabor_, cobertura_, tamaño_, precio_, fecha_, hora_,usuario.getDireccion()
                            , usuario.getTelefono(), usuario.getNombre(),
                            especificaciones_,"PENDIENTE");

                    reference.setValue(helperClass).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            nombre_cotizacion.setText("");
                            precio.setText("");
                            fecha.setText("FECHA DEL PEDIDO");
                            hora.setText("HORA DEL PEDIDO");
                            especificaciones.setText("");
                            Toast.makeText(getActivity(), "Cotización enviada", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}