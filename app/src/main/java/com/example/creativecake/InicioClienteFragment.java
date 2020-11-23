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
import android.widget.Button;

public class InicioClienteFragment extends Fragment {

    public InicioClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio_cliente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button boton_catalogo= view.findViewById(R.id.btn_cat);
        Button boton_cotizacion= view.findViewById(R.id.btn_coti);
        Button boton_carrito= view.findViewById(R.id.btn_carrito);
        Button boton_pedido= view.findViewById(R.id.btn_pedido);
        Button boton_usuario= view.findViewById(R.id.btn_usuario);

        final NavController navController= Navigation.findNavController(view);

        boton_catalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.catalogoClienteFragment);
            }
        });

        boton_cotizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.cotizacionClienteFragment);
            }
        });

        boton_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.carritoClienteFragment);
            }
        });

        boton_pedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.estadoPedidoFragment);
            }
        });

        boton_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.usuarioClienteFragment);
            }
        });
    }

    public interface OnFragmentInteractionListener {
    }
}