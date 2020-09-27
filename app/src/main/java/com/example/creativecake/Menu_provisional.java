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
                System.out.println(selectedId);
                Intent siguiente = new Intent(Menu_provisional.this, Inicio_domiciliario.class);
                startActivity(siguiente);
                // 2131230920 cliente
                // 2131230921 tienda
                // 2131230922 domicilio
                /*if (selectedId == 2131230920){
                    Intent siguiente = new Intent(Menu_provisional.this, Inicio_cliente.class);
                    startActivity(siguiente);
                } else if (selectedId == 2131230921){
                    Intent siguiente = new Intent(Menu_provisional.this, Inicio_Tienda.class);
                    startActivity(siguiente);
                } else if (selectedId == 2131230922){
                    System.out.println("Domiciliario");
                }

                 */
            }
        });
    }
}