package com.example.creativecake;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dialog_confirmacion {
    CotizacionHelperClass cotizacion;
    String telefono;
    StoreHelperClass tienda;
    Context globalContext;
    DatabaseReference ventas, domicilios, cotizaciones, chat,datosUsuario;

    public Dialog_confirmacion(final CotizacionHelperClass cotizacion, final String telefono, final Context globalContext) {
        this.cotizacion = cotizacion;
        this.telefono = telefono;
        this.globalContext = globalContext;

        final Dialog dialog = new Dialog(globalContext);
        dialog.requestWindowFeature((Window.FEATURE_NO_TITLE));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_confirmacion);

        domicilios= FirebaseDatabase.getInstance().getReference().child("domicilios");
        ventas = FirebaseDatabase.getInstance().getReference().child("Ventas");
        cotizaciones =FirebaseDatabase.getInstance().getReference().child("cotiTienda");
        chat =FirebaseDatabase.getInstance().getReference().child("chat");
        datosUsuario= FirebaseDatabase.getInstance().getReference("usuarioNegocio").child(telefono);
        datosUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tienda =snapshot.getValue(StoreHelperClass.class);
                try {
                    System.out.println(tienda.getNombre());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Button btn_SI = (Button) dialog.findViewById(R.id.btn_SIpuedo);
        Button btn_NO = (Button) dialog.findViewById(R.id.btn_NoPuedo);
        TextView nombreCoti = (TextView) dialog.findViewById(R.id.nomCoti);

        nombreCoti.setText(cotizacion.getNombre());

        btn_SI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemHelperClass producto = new ItemHelperClass(cotizacion.getNombre(),"1",tienda.getNombre(),tienda.getDireccion(),tienda.getTelefono(),cotizacion.getNombreCliente(),cotizacion.getDireccionCliente(),cotizacion.getNumeroCliente(),"Ninguna","0",cotizacion.getPrecio(),cotizacion.getFecha(),cotizacion.getHora());
                ClienteCotiHelper respuesta = new ClienteCotiHelper(tienda.getNombre(),tienda.getDireccion(),tienda.getTelefono(),cotizacion.getNombre(),cotizacion.getFecha(),cotizacion.getHora(),cotizacion.getPrecio());
                ventas.child(telefono).push().setValue(producto);
                domicilios.push().setValue(producto);
                chat.child(cotizacion.getNumeroCliente()).setValue(respuesta);
                cotizaciones.push().setValue(cotizacion);
                Toast.makeText(globalContext, "Recuerda seguir el estado del pago de este pedido en Pedidos Especiales", Toast.LENGTH_SHORT).show();
                Toast.makeText(globalContext, "¡Comienza a hacer tu pedido!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        btn_NO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(globalContext, "Gracias por a intención. ¡Sigue buscando cotizaciones!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
