package com.example.creativecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button iniciaSesion, registro;
    private EditText etTelefono, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciaSesion = (Button)findViewById(R.id.iniciaSesion);
        registro = (Button)findViewById(R.id.idBotonSiguiente);

        etTelefono = (EditText)findViewById(R.id.idTelefonoMain);
        etPassword = (EditText)findViewById(R.id.idPasswordMain);

        iniciaSesion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Login();
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registro = new Intent(getApplicationContext(), RegistroCliente.class);
                startActivity(registro);

            }
        });
    }

    private Boolean validarTelefono()
    {
        String telefono = etTelefono.getText().toString();
        if (telefono.isEmpty())
        {
            etTelefono.setError("No puede estar vacio");
            return false;
        }else
            {
                etTelefono.setError(null);
                return true;
            }
    }

    private Boolean validarPassword()
    {
        String password = etPassword.getText().toString();
        if (password.isEmpty())
        {
            etPassword.setError("No puede estar vacio");
            return false;
        }else
        {
            etPassword.setError(null);
            return true;
        }
    }

    public void Login()
    {
        if (!validarTelefono() | !validarPassword())
        {
            return;
        }else
            {
                isUser();
            }
    }

    private void isUser()
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

                    if (passwordfromDB.equals(passwordIngresado))
                    {
                        Intent intent = new Intent(getApplicationContext(), Menu_provisional.class);
                        startActivity(intent);
                    }else
                        {
                            etPassword.setError("Contraeña incorrecta");
                            etPassword.requestFocus();
                        }
                }else
                    {
                        etTelefono.setError("No está registrado el numero, registrate");
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}