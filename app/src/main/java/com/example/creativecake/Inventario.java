package com.example.creativecake;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.creativecake.R;

public class Inventario extends AppCompatActivity {

    Spinner tamaño;
    Spinner cobertura;
    Spinner sabor;
    Spinner decoración;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        tamaño= (Spinner)findViewById(R.id.tamaño);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(this, R.array.tamaño, android.R.layout.simple_spinner_item);
        tamaño.setAdapter(adapter1);

        cobertura= (Spinner)findViewById(R.id.cobertura);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(this, R.array.cobertura, android.R.layout.simple_spinner_item);
        cobertura.setAdapter(adapter2);

        sabor= (Spinner)findViewById(R.id.sabor);
        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this, R.array.sabor, android.R.layout.simple_spinner_item);
        sabor.setAdapter(adapter3);

        decoración= (Spinner)findViewById(R.id.decoracion);
        ArrayAdapter<CharSequence> adapter4=ArrayAdapter.createFromResource(this, R.array.decoracion, android.R.layout.simple_spinner_item);
        decoración.setAdapter(adapter4);
    }
}