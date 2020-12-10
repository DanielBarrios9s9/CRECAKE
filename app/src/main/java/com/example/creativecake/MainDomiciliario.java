package com.example.creativecake;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainDomiciliario extends AppCompatActivity {

    private Window window;

    private  static final int PERMISO_LOCATION = 1;

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
        final pedidosAceptadosDomiciliario fragment2 = new pedidosAceptadosDomiciliario();
        final CuentaDomiciliario fragment11 = new CuentaDomiciliario();

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

        historial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment3, fragment11);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        verificarPermisoLocation();

    }

    private void verificarPermisoLocation(){
        int estado_permiso = ContextCompat.checkSelfPermission(MainDomiciliario.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(estado_permiso == PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(MainDomiciliario.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISO_LOCATION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISO_LOCATION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    Toast.makeText(MainDomiciliario.this, "El permiso para la localización está concedido", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MainDomiciliario.this, "El permiso para la localización está denegado", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public void cambiarColor(){
        window.setStatusBarColor(Color.parseColor("#BF5A7F"));
    }
}