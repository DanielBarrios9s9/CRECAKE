package com.example.creativecake;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dialog_direcciones {

    private RecyclerView recyclerDirUsers, recyclerDirTiendas;
    Button btn_volver;
    Context context;
    ArrayList<String> tiendas;
    ArrayList<String> nombres;
    ArrayList<String> dir_tiendas;
    ArrayList<String> dir_users;
    DatabaseReference dirs;
    String telefono;
    AdapatadorDireccionesUser adapatadorDireccionesUser;
    AdapatdoDireccionesTienda adapatdoDireccionesTienda;

    public Dialog_direcciones(Context context, String telefono){

        this.context = context;
        this.telefono = telefono;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature((Window.FEATURE_NO_TITLE));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_direcciones);

        btn_volver = (Button) dialog.findViewById(R.id.btn_ruta);

        recyclerDirUsers = (RecyclerView) dialog.findViewById(R.id.recyclerDir_user);
        recyclerDirTiendas = (RecyclerView) dialog.findViewById(R.id.recyclerDir_tienda);
        recyclerDirTiendas.setLayoutManager(new LinearLayoutManager(context));
        recyclerDirUsers.setLayoutManager(new LinearLayoutManager(context));

        tiendas = new ArrayList<>();
        nombres = new ArrayList<>();
        dir_tiendas = new ArrayList<>();
        dir_users = new ArrayList<>();


        adapatadorDireccionesUser = new AdapatadorDireccionesUser(nombres, dir_users, context);
        recyclerDirUsers.setAdapter(adapatadorDireccionesUser);

        adapatdoDireccionesTienda = new AdapatdoDireccionesTienda(tiendas, dir_tiendas, context);
        recyclerDirTiendas.setAdapter(adapatdoDireccionesTienda);


        tiendas.add("Hola");

        Base();
        tiendas.add("Hola");

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }

    public void Base() {
        dirs = FirebaseDatabase.getInstance().getReference().getRoot().child("pedidoAgregadoDomiciliario").child(telefono);
        Query query = dirs.orderByChild("tienda");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tiendas.removeAll(tiendas);
                nombres.removeAll(nombres);
                dir_tiendas.removeAll(dir_tiendas);
                dir_users.removeAll(dir_users);

                for (DataSnapshot ds : snapshot.getChildren()) {

                    int count = 1;

                    for(DataSnapshot sn : ds.getChildren()){

                        if(!dir_tiendas.contains(sn.child("direccionTienda").getValue().toString()) && !tiendas.contains(sn.child("tienda").getValue().toString())){

                            dir_tiendas.add(sn.child("direccionTienda").getValue().toString());
                            tiendas.add(sn.child("tienda").getValue().toString());

                        }

                        if(ds.getChildrenCount() == count){

                            if(!dir_users.contains(sn.child("direccionUsuario").getValue().toString())) {

                                dir_users.add(sn.child("direccionUsuario").getValue().toString());
                                nombres.add(sn.child("usuario").getValue().toString());
                            }

                        }

                        count++;

                    }
                }
                adapatadorDireccionesUser.notifyDataSetChanged();
                adapatdoDireccionesTienda.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
