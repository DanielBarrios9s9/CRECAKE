package com.example.creativecake;

import android.app.Dialog;
import android.content.ClipData;
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

public class Dialog_entregado {
    ItemHelperClass producto;
    String telefono;
    Button si, no;
    DatabaseReference datos;

    public Dialog_entregado(final Context context,final ItemHelperClass producto, final String telefono) {
        this.producto = producto;
        this.telefono = telefono;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature((Window.FEATURE_NO_TITLE));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_entregado);

        no = (Button) dialog.findViewById(R.id.btn_NO);
        si = (Button) dialog.findViewById(R.id.btn_SI);

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    datos= FirebaseDatabase.getInstance().getReference().child("Ventas").child(telefono);
                    System.out.println(telefono);
                    Query deleteTienda = datos.orderByChild("producto").equalTo(producto.getProducto());
                    deleteTienda.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()) {
                                String clave= ds.getKey();
                                System.out.println(clave);
                                datos.child(clave).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "¡Gracias por cumplir con tu pedido!", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Error al buscar el producto", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }catch (Exception e){
                    Toast.makeText(context, "No se encuentra el producto", Toast.LENGTH_SHORT).show();
                }

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
