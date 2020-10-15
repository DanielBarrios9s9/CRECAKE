package com.example.creativecake;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class CotizacionClienteFragment extends Fragment {

    Spinner tamaño;
    Spinner cobertura;
    Spinner sabor;
    Spinner decoración;
    View vista;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        vista=inflater.inflate(R.layout.fragment_cotizacion_cliente, container, false);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button boton_enviar_coti= view.findViewById(R.id.btn_enviar);
        Button boton_revisar_coti= view.findViewById(R.id.btn_revisar);
        ImageButton boton_bot= view.findViewById(R.id.btn_bot);

        final NavController navController= Navigation.findNavController(view);

        boton_enviar_coti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navController.navigate(R.id.catalogoClienteFragment); falta saber qué enviar
            }
        });

        boton_revisar_coti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.revisarCotiClienteFragment);
            }
        });

        boton_bot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navController.navigate(R.id.cotizacionClienteFragment); abrir el bot
            }
        });

        tamaño= (Spinner)vista.findViewById(R.id.tamaño);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(getContext(), R.array.tamaño, android.R.layout.simple_spinner_item);
        tamaño.setAdapter(adapter1);

        cobertura= (Spinner)vista.findViewById(R.id.cobertura);
        ArrayAdapter<CharSequence> adapter2=ArrayAdapter.createFromResource(getContext(), R.array.cobertura, android.R.layout.simple_spinner_item);
        cobertura.setAdapter(adapter2);

        sabor= (Spinner)vista.findViewById(R.id.sabor);
        ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(getContext(), R.array.sabor, android.R.layout.simple_spinner_item);
        sabor.setAdapter(adapter3);

        decoración= (Spinner)vista.findViewById(R.id.decoracion);
        ArrayAdapter<CharSequence> adapter4=ArrayAdapter.createFromResource(getContext(), R.array.decoracion, android.R.layout.simple_spinner_item);
        decoración.setAdapter(adapter4);
    }
}