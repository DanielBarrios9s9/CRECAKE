package com.example.creativecake;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Direcciones {

    ArrayList<String> dir;
    String telefono;
    DatabaseReference dirs;
    Context context;
    Geocoder locations;
    ArrayList<String> latitud;
    ArrayList<String> longitud;


    public Direcciones(String tel, Context context){

        this.telefono = tel;
        this.context = context;
        locations = new Geocoder(context);
        latitud = new ArrayList<>();
        longitud = new ArrayList<>();

    }

    public void Base() {
        ArrayList<String> direcciones = new ArrayList<>();
        dirs = FirebaseDatabase.getInstance().getReference().getRoot().child("pedidoAgregadoDomiciliario").child(telefono);
        Query query = dirs.orderByChild("tienda");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                direcciones.removeAll(direcciones);
                for (DataSnapshot ds : snapshot.getChildren()) {

                    int count = 1;

                    for(DataSnapshot sn : ds.getChildren()){

                        if(!direcciones.contains(sn.child("direccionTienda").getValue().toString())){

                            direcciones.add(sn.child("direccionTienda").getValue().toString());

                        }

                        if(ds.getChildrenCount() == count){

                            if(direcciones.contains(sn.child("direccionUsuario").getValue().toString())) {

                                direcciones.remove(sn.child("direccionUsuario").getValue().toString());
                                int lastIndex = direcciones.size();
                                direcciones.add(lastIndex, sn.child("direccionUsuario").getValue().toString());
                            }else{
                                int lastIndex = direcciones.size();
                                direcciones.add(lastIndex, sn.child("direccionUsuario").getValue().toString());
                            }

                        }

                        count++;

                    }

                }

                System.out.println("DDDDDDDDDDDDDDDDDDDIiiiiiiiiiiiiiiiiiiiiiiiiiiirrrrrrrrrrrrrrrrrrrrrr: " + direcciones);
                coordenadas(direcciones);
                System.out.println("LLLLLLLLLLLLLLLLLLLAAAAAAAAAAAAATTTTTT: " + latitud);
                System.out.println("LLLLLLLLLLLLLLLLLLLOOOOOOOOOOOOONNNNNN: " + longitud);
                System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSTTTTTTTTTTTTTTTTTTTTTTTRRRRRRRRRRRRRRRRRRRRRRRRRRRRing: " + stringMaps(latitud, longitud));
                alMap(stringMaps(latitud, longitud));

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String stringMaps (ArrayList<String> lat, ArrayList<String> lon){

        String indications = new String();

        String string1 = "ll=";
        String string2 = "navigate=yes";

        int count = 1;

        for(int i = 0; i < lat.size(); i++){

            if( count == lat.size()){

                indications = indications + string1 + lat.get(i) + "," + lon.get(i);

            }else {

                indications = indications + string1 + lat.get(i) + "," + lon.get(i) + "&" + string2 + "&";

            }

            count++;

        }
        return indications;
    }

    private  void alMap(String indicacion){

        try {

            String url = "https://waze.com";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);

        } catch (ActivityNotFoundException ex) {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
            context.startActivity(intent);

        }

    }

    private void coordenadas(ArrayList<String> indicaciones){

        for(String punto : indicaciones){

            try {

                List<Address> addresses = locations.getFromLocationName(punto, 5);
                if(addresses.size() != 0) {

                    for(int i = 0; i < addresses.size(); i++) {

                        latitud.add(String.valueOf(addresses.get(i).getLatitude()));
                        longitud.add(String.valueOf(addresses.get(i).getLongitude()));

                    }
                }

            }catch (IOException e) {

                e.printStackTrace();

            }
        }

    }

}
