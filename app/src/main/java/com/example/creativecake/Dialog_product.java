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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Dialog_product{

    producto_ejemplo producto;
    UserHelperClass usuario;
    StoreHelperClass tienda;
    String telefono, item;
    DatabaseReference datosCarrito, datosUsuario, datosTienda;
    ImageView img;
    RatingBar ratingProducto;
    TextView nombre_producto, precio_producto, tipo_producto, descripcionProducto, ofertaProducto;

   public Dialog_product(final Context context, final producto_ejemplo producto, final String telefono){

       this.producto= producto;
       this.telefono=telefono;
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


       Picasso.get().load(producto.getDownloadUrl()).placeholder(R.drawable.imagenproducto). error(R.drawable.imagenproducto).resize(130,130).into(img);
       nombre_producto.setText(producto.getNombre());
       precio_producto.setText("$ " + producto.getPrecio());
       tipo_producto.setText(producto.getTipo());
       descripcionProducto.setText(producto.getDescripción());
       ratingProducto.setRating(Float.parseFloat(producto.getRating()));
       ofertaProducto.setText("- "+producto.getOferta()+" %");

       btn_agregar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               datosUsuario= FirebaseDatabase.getInstance().getReference("usuarioCliente").child(telefono);
               datosUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       usuario =snapshot.getValue(UserHelperClass.class);
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

               datosTienda= FirebaseDatabase.getInstance().getReference("usuarioNegocio");
               Query queryT = datosTienda.orderByChild("nombre").equalTo(producto.getUser_name());
               queryT.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for(DataSnapshot shot: snapshot.getChildren()){
                           tienda = shot.getValue(StoreHelperClass.class);
                           break;
                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });

               datosCarrito= FirebaseDatabase.getInstance().getReference().child("carrito").child(telefono);
               Query query = datosCarrito.limitToLast(1);
               query.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       for (DataSnapshot ds: snapshot.getChildren()) {
                           item = ds.getKey();
                           ItemHelperClass product = new ItemHelperClass(producto.getNombre(),"1", producto.getUser_name(), tienda.getDireccion(),
                                   tienda.getTelefono(), usuario.getNombre(), usuario.getDireccion(),telefono,
                                   producto.getDownloadUrl(),producto.getOferta(),producto.getPrecio(), " ", " ");

                           datosCarrito.child(item).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   Toast.makeText(context, "Producto agregado al Carrito", Toast.LENGTH_SHORT).show();
                                   int numItem = Integer.parseInt(item) + 1;
                                   String newItem = String.valueOf(numItem);
                                   datosCarrito.child(newItem).setValue(" ");
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(context, "Error al agregar el producto", Toast.LENGTH_SHORT).show();
                               }
                           });

                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
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
