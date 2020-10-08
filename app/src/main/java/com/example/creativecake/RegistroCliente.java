package com.example.creativecake;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroCliente extends AppCompatActivity
{
    FirebaseDatabase database;
    DatabaseReference reference;
    private EditText  etNombre, etCorreo, etPassword, etTelefono, etDireccion, etEdad;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cliente);

        mAuth = FirebaseAuth.getInstance();

        etNombre = (EditText)findViewById(R.id.idNombre);
        etCorreo = (EditText)findViewById(R.id.idCorreo);
        etPassword = (EditText)findViewById(R.id.idPassword);
        etTelefono = (EditText)findViewById(R.id.idTelefono);
        etDireccion = (EditText)findViewById(R.id.idDireccion);
        etEdad = (EditText)findViewById(R.id.idEdad);


        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("usuarioCliente");

        String nombre = etNombre.getText().toString();
        String correo = etCorreo.getText().toString();
        String password = etPassword.getText().toString();
        String telefono = etTelefono.getText().toString();
        String direccion = etDireccion.getText().toString();
        String edad = etEdad.getText().toString();

        UserHelperClass helperClass = new UserHelperClass(nombre, correo, password,telefono, direccion, edad);
        reference.setValue(helperClass);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }
}