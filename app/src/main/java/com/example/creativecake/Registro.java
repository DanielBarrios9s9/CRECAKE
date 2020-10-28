package com.example.creativecake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;

public class Registro extends AppCompatActivity
{
    private RadioButton rbCliente, rbNegocio, rbDomiciliario;
    private Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        rbCliente = (RadioButton)findViewById(R.id.idRbCliente);
        rbNegocio = (RadioButton)findViewById(R.id.idRbNegocio);
        rbDomiciliario = (RadioButton)findViewById(R.id.idRbDomiciliario);

        this.window = getWindow();
        cambiarColor();
    }

    public void SiguienteActivity(View view)
    {
        if (rbCliente.isChecked() == true)
        {
            Intent intent = new Intent(getApplicationContext(), RegistroCliente.class);
            startActivity(intent);
            finish();
        }else if (rbNegocio.isChecked() == true)
        {
            Intent intent = new Intent(getApplicationContext(), RegistroNegocio.class);
            startActivity(intent);
            finish();
        }else if (rbDomiciliario.isChecked()== true)
        {
            Intent intent = new Intent(getApplicationContext(), RegistroDomiciliario.class);
            startActivity(intent);
            finish();
        }
    }
    public void cambiarColor(){
        window.setStatusBarColor(Color.parseColor("#BF5A7F"));
    }

}