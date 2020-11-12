package com.example.creativecake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;

import com.google.android.material.navigation.NavigationView;

public class MainCliente extends AppCompatActivity{

    public static Context contextOfApplication;
    public static Context getContextOfApplication(){
        return contextOfApplication;
    }

    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);

        this.window = getWindow();
        cambiarColor();


    }

    public void cambiarColor(){
        window.setStatusBarColor(Color.parseColor("#BF5A7F"));
    }
}