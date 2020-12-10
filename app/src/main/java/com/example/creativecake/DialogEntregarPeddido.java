package com.example.creativecake;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DialogEntregarPeddido {

    Context globalContext;
    Button btn_entregar, btn_cancelar;
    String telefono, key;

    public DialogEntregarPeddido (Context globalContext, String telefono, String key){

        this.telefono= telefono;
        this.globalContext = globalContext;
        this.key = key;

        final Dialog dialog = new Dialog(globalContext);
        dialog.requestWindowFeature((Window.FEATURE_NO_TITLE));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_entregar_pedido);

        btn_entregar = (Button) dialog.findViewById(R.id.btn_SIpuedo);
        btn_cancelar = (Button) dialog.findViewById(R.id.btn_NoPuedo);

        btn_entregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                base();
                dialog.dismiss();

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

    private void base(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("entregasDomiciliario").child(telefono);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                System.out.println(" SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS: " + snapshot);
                if(snapshot.getValue() == null){

                    reference.setValue(1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("pedidoAgregadoDomiciliario").child(telefono).child(key);
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapshot.getRef().removeValue();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });

                }else{

                    Long count = (Long) snapshot.getValue();
                    count++;
                    reference.setValue(count).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("pedidoAgregadoDomiciliario").child(telefono).child(key);
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    snapshot.getRef().removeValue();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
