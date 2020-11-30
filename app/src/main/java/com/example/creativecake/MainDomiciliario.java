package com.example.creativecake;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MainDomiciliario extends AppCompatActivity {

    private Window window;

    Button disponibles, activos, historial;

    public static Context contextOfApplication;

    public static Context getContextOfApplication(){
        return contextOfApplication;
    }
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_domiciliario);
        this.window = getWindow();
        cambiarColor();

        disponibles = (Button) findViewById(R.id.ped_disponibles);
        activos = (Button) findViewById(R.id.ped_activos);
        historial = (Button) findViewById(R.id.historial);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        final InicioDomiciliarioFragment fragment1 = new InicioDomiciliarioFragment();
        final UbicacionDomiciliario fragment2 = new UbicacionDomiciliario();

        fragmentTransaction.add(R.id.fragment3, fragment1);
        fragmentTransaction.commit();

        disponibles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment3, fragment1);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        activos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment3, fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    public void cambiarColor(){
        window.setStatusBarColor(Color.parseColor("#BF5A7F"));
    }
}