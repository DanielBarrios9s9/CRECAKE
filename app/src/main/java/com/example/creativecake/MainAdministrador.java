package com.example.creativecake;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainAdministrador extends AppCompatActivity {

    //button14
    private Button cerrarsesion, btnAceptar, btnDenegar;
    private EditText noPedido, numeroAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrador);


        cerrarsesion = (Button)findViewById(R.id.button14);
        btnAceptar = (Button)findViewById(R.id.btnAceptar);
        btnDenegar = (Button)findViewById(R.id.btnDenegar);
        noPedido = (EditText)findViewById(R.id.noPedidoAceptar);
        numeroAceptar = (EditText)findViewById(R.id.numeroAceptar);

        cerrarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences_Util.savePhone_SP("", getApplicationContext());
                SharedPreferences_Util.savePassword_SP("", getApplicationContext());
                SharedPreferences_Util.saveType_SP("", getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aceptarPago();
            }
        });

        btnDenegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                denegarPago();
            }
        });

    }

    public void aceptarPago()
    {
        final String pedido = noPedido.getText().toString().trim();
        final String telefono = numeroAceptar.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pagoCarrito").child(telefono).child(pedido);

        reference.child("confirmacion").setValue("ACEPTADO");

        noPedido.setText("");
        numeroAceptar.setText("");
        noPedido.requestFocus();
    }

    public void denegarPago()
    {
        final String pedido = noPedido.getText().toString().trim();
        final String telefono = numeroAceptar.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("pagoCarrito").child(telefono).child(pedido);

        reference.child("confirmacion").setValue("DENEGADO");

        noPedido.setText("");
        numeroAceptar.setText("");
        noPedido.requestFocus();
    }
}