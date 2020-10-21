package com.example.creativecake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

public class Inicio_tienda extends AppCompatActivity {

    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_tienda);

        this.window = getWindow();
        cambiarColor();

    }

    public void cambiarColor(){
        window.setStatusBarColor(Color.parseColor("#BF5A7F"));
    }
}