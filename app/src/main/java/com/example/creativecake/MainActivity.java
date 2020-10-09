package com.example.creativecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button iniciaSesion;
    private Button registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniciaSesion = (Button)findViewById(R.id.iniciaSesion);
        registro = (Button)findViewById(R.id.registrate);

        iniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciaSesion = new Intent(MainActivity.this, RegistroCliente.class);
                startActivity(iniciaSesion);
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
}