package com.example.creativecake;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class Dialog_ProductoDomiciliario {
    ItemHelperClass producto;
    ImageView img;
    TextView nombre_producto, precio_producto, cantidad_producto, tiendaProducto;

    public Dialog_ProductoDomiciliario(final Context context, final ItemHelperClass producto){

        this.producto= producto;
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature((Window.FEATURE_NO_TITLE));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_producto_domiciliario);

        Button btn_cancelar = (Button) dialog.findViewById(R.id.btn_cancelar);
        img = (ImageView) dialog.findViewById(R.id.img_product);

        nombre_producto = (TextView) dialog.findViewById(R.id.text_product);
        precio_producto = (TextView) dialog.findViewById(R.id.text_precio);
        cantidad_producto = (TextView) dialog.findViewById(R.id.text_cant);
        tiendaProducto = (TextView) dialog.findViewById(R.id.text_tienda);

        Picasso.get().load(producto.getImagen()).placeholder(R.drawable.imagenproducto). error(R.drawable.imagenproducto).resize(130,130).into(img);
        nombre_producto.setText(producto.getProducto());
        precio_producto.setText("$ " + producto.getPrecio());
        cantidad_producto.setText("Cantidad: " + producto.getCantidad());
        tiendaProducto.setText(producto.getTienda());

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();
    }
}
