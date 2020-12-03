package com.example.creativecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.data.DataBufferObserverSet;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroCliente extends AppCompatActivity
{
    FirebaseDatabase database;
    DatabaseReference reference;
    private EditText  etNombre, etCorreo, etPassword, etTelefono, etDireccion, etEdad;
    private Button botonSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cliente);



        etNombre = (EditText)findViewById(R.id.idNombre);
        etCorreo = (EditText)findViewById(R.id.idCorreo);
        etPassword = (EditText)findViewById(R.id.idPassword);
        etTelefono = (EditText)findViewById(R.id.idTelefono);
        etDireccion = (EditText)findViewById(R.id.idDireccion);
        etEdad = (EditText)findViewById(R.id.idEdad);
        botonSiguiente = (Button)findViewById(R.id.botonSiguiente);


        // Write a message to the database
        botonSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("usuarioCliente");
                DatabaseReference ref2 = database.getReference().child("carrito");
                DatabaseReference ref3 = database.getReference("chat");
                DatabaseReference ref4 = database.getReference().child("pagoCarrito");
                DatabaseReference ref5 = database.getReference().child("cotizaciones");

                String nombre = etNombre.getText().toString();
                String correo = etCorreo.getText().toString();
                String password = etPassword.getText().toString();
                String telefono = etTelefono.getText().toString();
                String direccion = etDireccion.getText().toString();
                String edad = etEdad.getText().toString();

                UserHelperClass helperClass = new UserHelperClass(nombre, correo,
                        password,telefono, direccion, edad);

                reference.child(telefono).setValue(helperClass);
                ref2.child(telefono).child("1").setValue(" ");
                ref3.child(telefono).setValue(" ");
                ref4.child(telefono).child("1").setValue(" ");
                ref5.child(telefono).setValue(" ");

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                Bundle bundle = new Bundle();
                intent.putExtra("nombre", nombre);
                intent.putExtra("password", password);
                intent.putExtra("correo", correo);
                intent.putExtra("telefono", telefono);
                intent.putExtra("direccion", direccion);
                startActivity(intent);
                finish();
            }
        });
    }

}