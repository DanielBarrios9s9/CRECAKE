package com.example.creativecake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorProductoOferta extends RecyclerView.Adapter<AdaptadorProductoOferta.ViewHolderDatos> {
    ArrayList<p_ejemplo_oferta> listaProductos;

    public AdaptadorProductoOferta( ArrayList<p_ejemplo_oferta> listaProductos){
        this.listaProductos = listaProductos;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.p_ejemplo_oferta,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.imagenProducto.setImageResource(listaProductos.get(position).getImagenProducto());
        holder.nombreProducto.setText(listaProductos.get(position).getNombreProducto());
        holder.valorProducto.setText(listaProductos.get(position).getValorProducto());
        holder.pasteleriaProducto.setText(listaProductos.get(position).getPasteleriaProducto());
        holder.ofertaProducto.setText(listaProductos.get(position).getOfertaProducto());
        holder.ratingProducto.setNumStars((int) listaProductos.get(position).getRatingProducto());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        ImageView imagenProducto;
        TextView nombreProducto, valorProducto, pasteleriaProducto, ofertaProducto;
        RatingBar ratingProducto;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            imagenProducto= (ImageView) itemView.findViewById(R.id.imagen_producto);
            nombreProducto= (TextView) itemView.findViewById(R.id.nombre);
            valorProducto= (TextView) itemView.findViewById(R.id.precio_producto);
            pasteleriaProducto= (TextView) itemView.findViewById(R.id.pasteleria_producto);
            ofertaProducto= (TextView) itemView.findViewById(R.id.descuento_producto);
            ratingProducto= (RatingBar) itemView.findViewById(R.id.rating_producto);
        }
    }
}
