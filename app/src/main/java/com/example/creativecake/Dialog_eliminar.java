package com.example.creativecake;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

public class Dialog_eliminar {
    producto_ejemplo producto;
    TextView nombre_producto, precio_producto;
    DatabaseReference datosDelRef;

    public Dialog_eliminar(){}

    public Dialog_eliminar(final Context context, final producto_ejemplo producto){

        this.producto= producto;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature((Window.FEATURE_NO_TITLE));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_eliminar);

        Button btn_cancelar = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btn_eliminar = (Button) dialog.findViewById(R.id.btn_delete);

        nombre_producto = (TextView) dialog.findViewById(R.id.text_pro);
        precio_producto = (TextView) dialog.findViewById(R.id.text_pre);

        nombre_producto.setText(producto.getNombre());
        precio_producto.setText("$ " + producto.getPrecio());

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datosDelRef= FirebaseDatabase.getInstance().getReference("productoTienda");
                Query deleteTienda = datosDelRef.orderByChild("nombre").equalTo(producto.getNombre());
                deleteTienda.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) {
                            String clave= ds.getKey();
                            datosDelRef.child(clave).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "Se ha eliminado correctamente el producto", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
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
