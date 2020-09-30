package com.example.creativecake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Menu_provisional extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_provisional);

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        siguiente = (Button) findViewById(R.id.siguiente);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                radioButton = (RadioButton) findViewById(selectedId);

                if(radioButton.getText().equals("Cliente")){
                    Intent cliente = new Intent(Menu_provisional.this,
                            Inicio_cliente.class);
                    startActivity(cliente);
                    System.out.println("Cliente");
                } else if(radioButton.getText().equals("Negocio")){
                    Intent tienda = new Intent(Menu_provisional.this,
                            Inicio_tienda.class);
                    startActivity(tienda);
                } else if(radioButton.getText().equals("Domiciliario")) {
                    Intent domiciliario = new Intent(Menu_provisional.this,
                            Inicio_domiciliario.class);
                    startActivity(domiciliario);
                }
            }
        });
    }
}