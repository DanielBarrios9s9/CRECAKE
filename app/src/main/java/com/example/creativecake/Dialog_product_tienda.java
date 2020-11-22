package com.example.creativecake;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Dialog_product_tienda {
    producto_ejemplo producto;
    ImageView img;
    RatingBar ratingProducto;
    TextView nombre_producto, precio_producto, tipo_producto, descripcionProducto, cantidadProducto, ofertaProducto;

    public Dialog_product_tienda(){}

    public Dialog_product_tienda(Context context, producto_ejemplo producto){

        this.producto= producto;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature((Window.FEATURE_NO_TITLE));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_producto_tienda);

        Button btn_cancelar = (Button) dialog.findViewById(R.id.btn_cancelar);
        img = (ImageView) dialog.findViewById(R.id.img_product);

        nombre_producto = (TextView) dialog.findViewById(R.id.text_product);
        precio_producto = (TextView) dialog.findViewById(R.id.text_precio);
        tipo_producto = (TextView) dialog.findViewById(R.id.text_tipo);
        descripcionProducto = (TextView) dialog.findViewById(R.id.text_descripcion);
        ofertaProducto = (TextView) dialog.findViewById(R.id.text_oferta);
        ratingProducto =(RatingBar) dialog.findViewById(R.id.ratingBar);
        cantidadProducto=(TextView) dialog.findViewById(R.id.text_cantidad);

        ofertaProducto.setVisibility(View.GONE);

        Picasso.get().load(producto.getDownloadUrl()).placeholder(R.drawable.imagenproducto). error(R.drawable.imagenproducto).resize(130,130).into(img);
        nombre_producto.setText(producto.getNombre());
        precio_producto.setText("$ " + producto.getPrecio());
        tipo_producto.setText(producto.getTipo());
        descripcionProducto.setText(producto.getDescripción());
        ratingProducto.setRating(Float.parseFloat(producto.getRating()));
        cantidadProducto.setText(producto.getCantidad());
        if (producto.getOferta()==""){
            ofertaProducto.setVisibility(View.INVISIBLE);
        }
        else if (producto.getOferta()==" ") {
            ofertaProducto.setVisibility(View.INVISIBLE);
        }
        else if (producto.getOferta()=="0") {
            ofertaProducto.setVisibility(View.INVISIBLE);
        }
        else{
            ofertaProducto.setVisibility(View.VISIBLE);
            ofertaProducto.setText("- "+producto.getOferta()+" %");
        }

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        dialog.show();
    }
}
