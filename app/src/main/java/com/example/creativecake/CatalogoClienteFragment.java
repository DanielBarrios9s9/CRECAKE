package com.example.creativecake;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CatalogoClienteFragment extends Fragment {
    View v;
    AdaptadorProductoCatalogo adaptador;
    private RecyclerView recyclerProductos;
    private ArrayList<producto_ejemplo> listaProductos;
    private DatabaseReference datosCatRef;
    CheckBox tipoTorta, tipoPostre, tipoHojaldre, tipoOtro;
    TextInputEditText precio;

    public CatalogoClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_catalogo_cliente,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v=view;
        Inicializar();
        Base();
    }

    public void Inicializar(){
        recyclerProductos = (RecyclerView) v.findViewById(R.id.recyclerview);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaProductos = new ArrayList<>();
        adaptador = new AdaptadorProductoCatalogo(listaProductos);
        recyclerProductos.setAdapter(adaptador);

        tipoTorta = (CheckBox) v.findViewById(R.id.checkTorta);
        tipoPostre = (CheckBox) v.findViewById(R.id.checkPostre);
        tipoHojaldre = (CheckBox) v.findViewById(R.id.checkHojaldre);
        tipoOtro = (CheckBox) v.findViewById(R.id.checkOtro);
    }

    public void Base(){
        datosCatRef= FirebaseDatabase.getInstance().getReference().getRoot().child("productoTienda");
        datosCatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    producto_ejemplo producto = ds.getValue(producto_ejemplo.class);
                    listaProductos.add(producto);
                }
                adaptador.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
/*
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<producto_ejemplo> opciones = new FirebaseRecyclerOptions.Builder<producto_ejemplo>().setQuery(query,producto_ejemplo.class).build();
        FirebaseRecyclerAdapter adapter = new FirebaseRecyclerAdapter<producto_ejemplo,ViewHolderDatos>(opciones){


            @NonNull
            @Override
            public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.producto_ejemplo,parent,false);
                return new ViewHolderDatos(view);
            }

            @Override
            public void onBindViewHolder(final ViewHolderDatos holder, int posicion, producto_ejemplo modelo) {
                final String productosIDs= getRef(posicion).getKey();

                datosCatRef.child(productosIDs);
                datosCatRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if ((snapshot.child("oferta").getValue().toString() == " ") && (snapshot.child("oferta").getValue() != null)){
                            if (tipoTorta.isChecked()) {
                                if (snapshot.child("tipo").getValue().toString() == "Torta") {
                                    //String imagenProducto = snapshot.child("downloadUrl").getValue().toString();
                                    String nombreProducto = snapshot.child("nombre").getValue().toString();
                                    String valorProducto = snapshot.child("precio").getValue().toString();
                                    String pasteleriaProducto = snapshot.child("user_name").getValue().toString();
                                    String tipoProducto = "Torta";
                                    String ratingProducto = snapshot.child("rating").getValue().toString();

                                    //Picasso.get().load(imagenProducto).placeholder(R.drawable.imagenproducto).into(holder.imagenProducto);
                                    holder.nombreProducto.setText(nombreProducto);
                                    holder.valorProducto.setText(valorProducto);
                                    holder.pasteleriaProducto.setText(pasteleriaProducto);
                                    holder.tipoProducto.setText(tipoProducto);
                                    holder.ratingProducto.setNumStars(Integer.valueOf(ratingProducto));
                                }
                            }
                            else if (tipoPostre.isChecked()){
                                if (snapshot.child("tipo").getValue().toString() == "Postre"){
                                    //String imagenProducto = snapshot.child("downloadUrl").getValue().toString();
                                    String nombreProducto = snapshot.child("nombre").getValue().toString();
                                    String valorProducto = snapshot.child("precio").getValue().toString();
                                    String pasteleriaProducto = snapshot.child("user_name").getValue().toString();
                                    String tipoProducto = "Postre";
                                    String ratingProducto = snapshot.child("rating").getValue().toString();

                                    //Picasso.get().load(imagenProducto).placeholder(R.drawable.imagenproducto).into(holder.imagenProducto);
                                    holder.nombreProducto.setText(nombreProducto);
                                    holder.valorProducto.setText(valorProducto);
                                    holder.pasteleriaProducto.setText(pasteleriaProducto);
                                    holder.tipoProducto.setText(tipoProducto);
                                    holder.ratingProducto.setNumStars(Integer.valueOf(ratingProducto));
                                }
                            }
                            else if (tipoHojaldre.isChecked()){
                                if (snapshot.child("tipo").getValue().toString() == "Hojaldre"){
                                    //String imagenProducto = snapshot.child("downloadUrl").getValue().toString();
                                    String nombreProducto = snapshot.child("nombre").getValue().toString();
                                    String valorProducto = snapshot.child("precio").getValue().toString();
                                    String pasteleriaProducto = snapshot.child("user_name").getValue().toString();
                                    String tipoProducto = "Hojaldre";
                                    String ratingProducto = snapshot.child("rating").getValue().toString();

                                    //Picasso.get().load(imagenProducto).placeholder(R.drawable.imagenproducto).into(holder.imagenProducto);
                                    holder.nombreProducto.setText(nombreProducto);
                                    holder.valorProducto.setText(valorProducto);
                                    holder.pasteleriaProducto.setText(pasteleriaProducto);
                                    holder.tipoProducto.setText(tipoProducto);
                                    holder.ratingProducto.setNumStars(Integer.valueOf(ratingProducto));
                                }
                            }
                            else if (tipoOtro.isChecked()){
                                if (snapshot.child("tipo").getValue().toString() == "Otro"){
                                    //String imagenProducto = snapshot.child("downloadUrl").getValue().toString();
                                    String nombreProducto = snapshot.child("nombre").getValue().toString();
                                    String valorProducto = snapshot.child("precio").getValue().toString();
                                    String pasteleriaProducto = snapshot.child("user_name").getValue().toString();
                                    String tipoProducto = "Otro";
                                    String ratingProducto = snapshot.child("rating").getValue().toString();

                                    //Picasso.get().load(imagenProducto).placeholder(R.drawable.imagenproducto).into(holder.imagenProducto);
                                    holder.nombreProducto.setText(nombreProducto);
                                    holder.valorProducto.setText(valorProducto);
                                    holder.pasteleriaProducto.setText(pasteleriaProducto);
                                    holder.tipoProducto.setText(tipoProducto);
                                    holder.ratingProducto.setNumStars(Integer.valueOf(ratingProducto));
                                }
                            }
                            else{
                                //String imagenProducto = snapshot.child("downloadUrl").getValue().toString();
                                String nombreProducto = snapshot.child("nombre").getValue().toString();
                                String valorProducto = snapshot.child("precio").getValue().toString();
                                String pasteleriaProducto = snapshot.child("user_name").getValue().toString();
                                String tipoProducto = snapshot.child("tipo").getValue().toString();;
                                String ratingProducto = snapshot.child("rating").getValue().toString();

                                //Picasso.get().load(imagenProducto).placeholder(R.drawable.imagenproducto).into(holder.imagenProducto);
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
    }*/
}