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

    private Button iniciaSesionCliente, registro, iniciaSesionDom, iniciaSesionNeg, iniciaSesionAdmin;
    private EditText etTelefono, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciaSesionCliente = (Button)findViewById(R.id.iniciaSesionCliente);
        registro = (Button)findViewById(R.id.idBotonRegistro);
        iniciaSesionDom = (Button)findViewById(R.id.iniciaSesionDomiciliario);
        iniciaSesionNeg = (Button)findViewById(R.id.iniciaSesionNegocio);
        iniciaSesionAdmin = (Button)findViewById(R.id.iniciaSesionAdmin);

        etTelefono = (EditText)findViewById(R.id.idTelefonoMain);
        etPassword = (EditText)findViewById(R.id.idPasswordMain);

        iniciaSesionCliente.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LoginCliente();
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent registro = new Intent(getApplicationContext(), Registro.class);
                startActivity(registro);

            }
        });

        iniciaSesionDom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDomiciliario();
            }
        });

        iniciaSesionNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginStore();
            }
        });

        iniciaSesionAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginDeveloper();
            }
        });

        System.out.println(SharedPreferences_Util.getPhone_SP(this));
        System.out.println(SharedPreferences_Util.getPassword_SP(this));
        System.out.println(SharedPreferences_Util.getType_SP(this));

        if(SharedPreferences_Util.getPhone_SP(this) != null && SharedPreferences_Util.getType_SP(this) != null) {
            if(!SharedPreferences_Util.getPhone_SP(this).equals("")) {
                if(SharedPreferences_Util.getType_SP(this).equals("User")) {
                    Intent intent = new Intent(getApplicationContext(), MainCliente.class);
                    startActivity(intent);
                    finish();
                } else if (SharedPreferences_Util.getType_SP(this).equals("Tienda")) {
                    Intent intent = new Intent(getApplicationContext(), Inicio_tienda.class);
                    startActivity(intent);
                    finish();
                } else if (SharedPreferences_Util.getType_SP(this).equals("Domiciliario")) {
                    Intent intent = new Intent(getApplicationContext(), MainDomiciliario.class);
                    startActivity(intent);
                    finish();
                } else if (SharedPreferences_Util.getType_SP(this).equals("Admin")) {
                    Intent intent = new Intent(getApplicationContext(), MainAdministrador.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
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

    public void LoginCliente()
    {
        if (!validarTelefono() | !validarPassword())
        {
            return;
        }else
            {
                isUser();
            }
    }

    public void LoginDomiciliario()
    {
        if (!validarTelefono() | !validarPassword())
        {
            return;
        }else
        {
            isDomiciliary();
        }
    }

    public void LoginStore()
    {
        if (!validarTelefono() | !validarPassword())
        {
            return;
        }else
        {
            isStore();
        }
    }

    public void LoginDeveloper()
    {
        if (!validarTelefono() | !validarPassword())
        {
            return;
        }else
        {
            isDeveloper();
        }
    }

    private void isDeveloper()
    {
        final String numeroIngresado = etTelefono.getText().toString().trim();
        final String passwordIngresado = etPassword.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarioDev");

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
                        SharedPreferences_Util.savePhone_SP(numeroIngresado, getApplication().getApplicationContext());
                        SharedPreferences_Util.savePassword_SP(passwordIngresado, getApplication().getApplicationContext());
                        SharedPreferences_Util.saveType_SP("Admin", getApplication().getApplicationContext());
                        Intent intent = new Intent(getApplicationContext(), MainAdministrador.class);
                        startActivity(intent);
                        finish();
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
                        SharedPreferences_Util.savePhone_SP(numeroIngresado, getApplicationContext());
                        SharedPreferences_Util.savePassword_SP(passwordIngresado, getApplicationContext());
                        SharedPreferences_Util.saveType_SP("User", getApplication().getApplicationContext());
                        Intent intent = new Intent(getApplicationContext(), MainCliente.class);
                        startActivity(intent);
                        finish();
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

    private void isDomiciliary()
    {
        final String numeroIngresado = etTelefono.getText().toString().trim();
        final String passwordIngresado = etPassword.getText().toString().trim();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarioDomiciliario");

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
                        SharedPreferences_Util.savePhone_SP(numeroIngresado, getApplication().getApplicationContext());
                        SharedPreferences_Util.savePassword_SP(passwordIngresado, getApplication().getApplicationContext());
                        SharedPreferences_Util.saveType_SP("Domiciliario", getApplication().getApplicationContext());
                        Intent intent = new Intent(getApplicationContext(), MainDomiciliario.class);
                        startActivity(intent);
                        finish();
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

    private void isStore()
    {
        final String numeroIngresado = etTelefono.getText().toString().trim();
        final String passwordIngresado = etPassword.getText().toString().trim();


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarioNegocio");

        Query checkUsuario = reference.orderByChild("telefono").equalTo(numeroIngresado);

        checkUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists())
                {
                    etTelefono.setError(null);


                    String namefromDB = snapshot.child(numeroIngresado).child("nombre").getValue(String.class);
                    String passwordfromDB = snapshot.child(numeroIngresado).child("password").getValue(String.class);

                    if (passwordfromDB.equals(passwordIngresado))
                    {
                        SharedPreferences_Util.savePhone_SP(numeroIngresado, getApplication().getApplicationContext());
                        SharedPreferences_Util.savePassword_SP(passwordIngresado, getApplication().getApplicationContext());
                        SharedPreferences_Util.saveType_SP("Tienda", getApplication().getApplicationContext());
                        Intent intent = new Intent(getApplicationContext(), Inicio_tienda.class);
                        intent.putExtra("nombreIngresado", namefromDB);
                        startActivity(intent);
                        finish();
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