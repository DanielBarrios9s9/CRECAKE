package com.example.creativecake;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class Dialog_product{

    producto_ejemplo producto;
    ImageView img;
    RatingBar ratingProducto;
    TextView nombre_producto, precio_producto, tipo_producto, descripcionProducto, ofertaProducto;

   public Dialog_product(Context context, producto_ejemplo producto){

       this.producto= producto;

       final Dialog dialog = new Dialog(context);
       dialog.requestWindowFeature((Window.FEATURE_NO_TITLE));
       dialog.setCancelable(false);
       dialog.setContentView(R.layout.dialog_producto);

       Button btn_cancelar = (Button) dialog.findViewById(R.id.btn_cancelar);
       Button btn_agregar = (Button) dialog.findViewById(R.id.btn_agregar);
       img = (ImageView) dialog.findViewById(R.id.img_product);

       nombre_producto = (TextView) dialog.findViewById(R.id.text_product);
       precio_producto = (TextView) dialog.findViewById(R.id.text_precio);
       tipo_producto = (TextView) dialog.findViewById(R.id.text_tipo);
       descripcionProducto = (TextView) dialog.findViewById(R.id.text_descripcion);
       ofertaProducto = (TextView) dialog.findViewById(R.id.txt_off);
       ratingProducto =(RatingBar) dialog.findViewById(R.id.ratingBar);

       ofertaProducto.setVisibility(View.GONE);

       Picasso.get().load(producto.getDownloadUrl()).placeholder(R.drawable.imagenproducto). error(R.drawable.imagenproducto).resize(130,130).into(img);
       nombre_producto.setText(producto.getNombre());
       precio_producto.setText("$ " + producto.getPrecio());
       tipo_producto.setText(producto.getTipo());
       descripcionProducto.setText(producto.getDescripción());
       ratingProducto.setRating(Float.parseFloat(producto.getRating()));

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

       btn_agregar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

       btn_cancelar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               dialog.dismiss();

           }
       });

       dialog.show();
   }




}
