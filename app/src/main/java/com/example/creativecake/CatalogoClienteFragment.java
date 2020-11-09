package com.example.creativecake;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CatalogoClienteFragment extends Fragment {
    private RecyclerView recyclerProductos;
    private DatabaseReference datosCatRef;
    CheckBox tipoTorta, tipoPostre, tipoHojaldre, tipoOtro;
    TextInputEditText precio;

    public CatalogoClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_catalogo_cliente,container,false);
        recyclerProductos = vista.findViewById(R.id.recyclerview);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        
        datosCatRef= FirebaseDatabase.getInstance().getReference().child("productoTienda");

        tipoTorta = (CheckBox) vista.findViewById(R.id.checkTorta);
        tipoPostre = (CheckBox) vista.findViewById(R.id.checkPostre);
        tipoHojaldre = (CheckBox) vista.findViewById(R.id.checkHojaldre);
        tipoOtro = (CheckBox) vista.findViewById(R.id.checkOtro);

        return vista;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions opciones = new FirebaseRecyclerOptions.Builder<p_ejemplo_cat>().setQuery(datosCatRef,p_ejemplo_cat.class).build();
        FirebaseRecyclerAdapter<p_ejemplo_cat,ViewHolderDatos> adapter = new FirebaseRecyclerAdapter<p_ejemplo_cat,ViewHolderDatos>(opciones){

            @NonNull
            @Override
            public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.p_ejemplo_cat,null,false);
                return new ViewHolderDatos(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolderDatos holder, int i, @NonNull p_ejemplo_cat p_ejemplo_cat) {
                final String productosIDs= getRef(i).getKey();

                datosCatRef.child(productosIDs);
                datosCatRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("oferta").getValue().toString() == " "){
                            if (tipoTorta.isChecked()) {
                                if (snapshot.child("tipo").getValue().toString() == "Torta") {
                                    String imagenProducto = snapshot.child("downloadUrl").getValue().toString();
                                    String nombreProducto = snapshot.child("nombre").getValue().toString();
                                    String valorProducto = snapshot.child("precio").getValue().toString();
                                    String pasteleriaProducto = snapshot.child("user_name").getValue().toString();
                                    String tipoProducto = "Torta";
                                    String ratingProducto = snapshot.child("rating").getValue().toString();

                                    Picasso.get().load(imagenProducto).placeholder(R.drawable.imagenproducto).into(holder.imagenProducto);
                                    holder.nombreProducto.setText(nombreProducto);
                                    holder.valorProducto.setText(valorProducto);
                                    holder.pasteleriaProducto.setText(pasteleriaProducto);
                                    holder.tipoProducto.setText(tipoProducto);
                                    holder.ratingProducto.setNumStars(Integer.valueOf(ratingProducto));
                                }
                            }
                            else if (tipoPostre.isChecked()){
                                if (snapshot.child("tipo").getValue().toString() == "Postre"){
                                    String imagenProducto = snapshot.child("downloadUrl").getValue().toString();
                                    String nombreProducto = snapshot.child("nombre").getValue().toString();
                                    String valorProducto = snapshot.child("precio").getValue().toString();
                                    String pasteleriaProducto = snapshot.child("user_name").getValue().toString();
                                    String tipoProducto = "Postre";
                                    String ratingProducto = snapshot.child("rating").getValue().toString();

                                    Picasso.get().load(imagenProducto).placeholder(R.drawable.imagenproducto).into(holder.imagenProducto);
                                    holder.nombreProducto.setText(nombreProducto);
                                    holder.valorProducto.setText(valorProducto);
                                    holder.pasteleriaProducto.setText(pasteleriaProducto);
                                    holder.tipoProducto.setText(tipoProducto);
                                    holder.ratingProducto.setNumStars(Integer.valueOf(ratingProducto));
                                }
                            }
                            else if (tipoHojaldre.isChecked()){
                                if (snapshot.child("tipo").getValue().toString() == "Hojaldre"){
                                    String imagenProducto = snapshot.child("downloadUrl").getValue().toString();
                                    String nombreProducto = snapshot.child("nombre").getValue().toString();
                                    String valorProducto = snapshot.child("precio").getValue().toString();
                                    String pasteleriaProducto = snapshot.child("user_name").getValue().toString();
                                    String tipoProducto = "Hojaldre";
                                    String ratingProducto = snapshot.child("rating").getValue().toString();

                                    Picasso.get().load(imagenProducto).placeholder(R.drawable.imagenproducto).into(holder.imagenProducto);
                                    holder.nombreProducto.setText(nombreProducto);
                                    holder.valorProducto.setText(valorProducto);
                                    holder.pasteleriaProducto.setText(pasteleriaProducto);
                                    holder.tipoProducto.setText(tipoProducto);
                                    holder.ratingProducto.setNumStars(Integer.valueOf(ratingProducto));
                                }
                            }
                            else if (tipoOtro.isChecked()){
                                if (snapshot.child("tipo").getValue().toString() == "Otro"){
                                    String imagenProducto = snapshot.child("downloadUrl").getValue().toString();
                                    String nombreProducto = snapshot.child("nombre").getValue().toString();
                                    String valorProducto = snapshot.child("precio").getValue().toString();
                                    String pasteleriaProducto = snapshot.child("user_name").getValue().toString();
                                    String tipoProducto = "Otro";
                                    String ratingProducto = snapshot.child("rating").getValue().toString();

                                    Picasso.get().load(imagenProducto).placeholder(R.drawable.imagenproducto).into(holder.imagenProducto);
                                    holder.nombreProducto.setText(nombreProducto);
                                    holder.valorProducto.setText(valorProducto);
                                    holder.pasteleriaProducto.setText(pasteleriaProducto);
                                    holder.tipoProducto.setText(tipoProducto);
                                    holder.ratingProducto.setNumStars(Integer.valueOf(ratingProducto));
                                }
                            }
                            else{
                                String imagenProducto = snapshot.child("downloadUrl").getValue().toString();
                                String nombreProducto = snapshot.child("nombre").getValue().toString();
                                String valorProducto = snapshot.child("precio").getValue().toString();
                                String pasteleriaProducto = snapshot.child("user_name").getValue().toString();
                                String tipoProducto = snapshot.child("tipo").getValue().toString();;
                                String ratingProducto = snapshot.child("rating").getValue().toString();

                                Picasso.get().load(imagenProducto).placeholder(R.drawable.imagenproducto).into(holder.imagenProducto);
                                holder.nombreProducto.setText(nombreProducto);
                                holder.valorProducto.setText(valorProducto);
                                holder.pasteleriaProducto.setText(pasteleriaProducto);
                                holder.tipoProducto.setText(tipoProducto);
                                holder.ratingProducto.setNumStars(Integer.valueOf(ratingProducto));
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        };

        recyclerProductos.setAdapter(adapter);
        adapter.startListening();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        ImageView imagenProducto;
        TextView nombreProducto, valorProducto, pasteleriaProducto, tipoProducto;
        RatingBar ratingProducto;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            imagenProducto= (ImageView) itemView.findViewById(R.id.imagen_producto);
            nombreProducto= (TextView) itemView.findViewById(R.id.nombre_producto);
            valorProducto= (TextView) itemView.findViewById(R.id.precio_producto);
            pasteleriaProducto= (TextView) itemView.findViewById(R.id.pasteleria_producto);
            tipoProducto= (TextView) itemView.findViewById(R.id.tipo_producto);
            ratingProducto= (RatingBar) itemView.findViewById(R.id.rating_producto);
        }
    }
}