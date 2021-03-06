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

public class RegistroDomiciliario extends AppCompatActivity
{
    DatabaseReference reference;
    FirebaseDatabase database;
    EditText etNombreDomiciliario, etCorreoDomiciliario, etCuentaDomiciliario, etTelefonoDomiciliario, etPasswordDomiciliario;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_domiciliario);

        etNombreDomiciliario = (EditText)findViewById(R.id.idNombreDomiciliario);
        etCorreoDomiciliario = (EditText)findViewById(R.id.idCorreoDomiciliario);
        etCuentaDomiciliario = (EditText)findViewById(R.id.idCuentaDomiciliario);
        etTelefonoDomiciliario = (EditText)findViewById(R.id.idTelefonoDomiciliario);
        etPasswordDomiciliario = (EditText)findViewById(R.id.idPasswordDomiciliario);
        boton = (Button)findViewById(R.id.idBotonRegistro);


        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("usuarioDomiciliario");

                String nombre = etNombreDomiciliario.getText().toString();
                String correo = etCorreoDomiciliario.getText().toString();
                String cuenta = etCuentaDomiciliario.getText().toString();
                String telefono = etTelefonoDomiciliario.getText().toString();
                String password = etPasswordDomiciliario.getText().toString();

                DomiciliaryHelperClass domiciliaryHelperClass = new DomiciliaryHelperClass(nombre, correo, cuenta, telefono,password);

                reference.child(telefono).setValue(domiciliaryHelperClass).addOnSuccessListener(new OnSuccessListener<Void>() {
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