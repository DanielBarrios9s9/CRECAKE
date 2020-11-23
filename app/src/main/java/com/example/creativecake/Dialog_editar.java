package com.example.creativecake;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Dialog_editar {
    producto_ejemplo producto;
    EditText nombre_producto, precio_producto, descripcionProducto, ofertaProducto,cantidadProducto;
    Spinner tipo_producto;
    DatabaseReference reference;
    String clave;

    public Dialog_editar(){}

    public Dialog_editar(final Context context, final producto_ejemplo producto){

        this.producto= producto;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature((Window.FEATURE_NO_TITLE));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_editar);

        Button btn_cancelar = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_actualizar = (Button) dialog.findViewById(R.id.btn_actualizar);

        nombre_producto = (EditText) dialog.findViewById(R.id.edit_product);
        precio_producto = (EditText) dialog.findViewById(R.id.edit_precio);
        tipo_producto = (Spinner) dialog.findViewById(R.id.edit_tipo);
        descripcionProducto = (EditText) dialog.findViewById(R.id.edit_descripcion);
        ofertaProducto = (EditText) dialog.findViewById(R.id.edit_oferta);
        cantidadProducto = (EditText) dialog.findViewById(R.id.edit_Cantidad);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.Tipo_producto, android.R.layout.simple_spinner_item);

        tipo_producto.setAdapter(adapter);


        nombre_producto.setHint(producto.getNombre());
        precio_producto.setHint(producto.getPrecio());
        descripcionProducto.setHint(producto.getDescripción());
        ofertaProducto.setHint(producto.getOferta());
        cantidadProducto.setHint(producto.getCantidad());

        reference = FirebaseDatabase.getInstance().getReference("productoTienda");
        Query updateTienda = reference.orderByChild("nombre").equalTo(producto.getNombre());
        updateTienda.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    if (ds.child("user_name").getValue().equals(producto.getUser_name())) {
                        clave= ds.getKey();
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map < String, Object > usuarioMap = new HashMap<>();
                usuarioMap.put("cantidad",cantidadProducto.getText().toString());
                usuarioMap.put("descripción", descripcionProducto.getText().toString());
                usuarioMap.put("nombre",nombre_producto.getText().toString());
                usuarioMap.put("oferta", ofertaProducto.getText().toString());
                usuarioMap.put("precio", precio_producto.getText().toString());
                usuarioMap.put("tipo",tipo_producto.getItemAtPosition(tipo_producto.getSelectedItemPosition()).toString());

                reference.child(clave).updateChildren(usuarioMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(context, "Cambios guardados", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
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
