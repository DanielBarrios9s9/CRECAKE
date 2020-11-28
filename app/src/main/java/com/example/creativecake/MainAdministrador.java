package com.example.creativecake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainAdministrador extends AppCompatActivity {

    //button14
    private Button cerrarsesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_administrador);



        cerrarsesion = (Button)findViewById(R.id.button14);

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

    }
}