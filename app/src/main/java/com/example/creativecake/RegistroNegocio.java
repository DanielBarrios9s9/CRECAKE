package com.example.creativecake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroNegocio extends AppCompatActivity
{
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private EditText etNombreNegocio, etCorreoNegocio, etDireccionNegocio, etTelefonoNegocio, etPasswordNegocio;
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_negocio);

        etNombreNegocio = (EditText)findViewById(R.id.idNombreNegocio);
        etCorreoNegocio = (EditText)findViewById(R.id.idCorreoNegocio);
        etDireccionNegocio = (EditText)findViewById(R.id.idDireccionNegocio);
        etTelefonoNegocio = (EditText)findViewById(R.id.idTelefonoNegocio);
        etPasswordNegocio = (EditText)findViewById(R.id.idPasswordNegocio);
        boton = (Button)findViewById(R.id.idBotonRegistro);


        boton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("usuarioNegocio");
                DatabaseReference ref2 = database.getReference("Ventas");

                String nombreNegocio = etNombreNegocio.getText().toString();
                String correoNegocio = etCorreoNegocio.getText().toString();
                String direccionNegocio = etDireccionNegocio.getText().toString();
                String telefonoNegocio = etTelefonoNegocio.getText().toString();
                String passwordNegocio = etPasswordNegocio.getText().toString();


                StoreHelperClass storeHelperClass = new StoreHelperClass(nombreNegocio, correoNegocio, direccionNegocio, telefonoNegocio, passwordNegocio);

                reference.child(telefonoNegocio).setValue(storeHelperClass);
                ref2.child(telefonoNegocio).setValue(" ").addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), "Nuevo usuario registrado", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}