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

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.ViewHolderDatos> {
    ArrayList<p_ejemplo_cat> listaProductos;

    public AdaptadorProductos(ArrayList<p_ejemplo_cat> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @Override
    public ViewHolderDatos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.p_ejemplo_cat,null,false);
        return new ViewHolderDatos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatos holder, int position) {
        holder.imagenProducto.setImageResource(listaProductos.get(position).getImagenProducto());
        holder.nombreProducto.setText(listaProductos.get(position).getNombreProducto());
        holder.valorProducto.setText(listaProductos.get(position).getValorProducto());
        holder.pasteleriaProducto.setText(listaProductos.get(position).getPasteleriaProducto());
        holder.ratingProducto.setNumStars((int) listaProductos.get(position).getRatingProducto());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewHolderDatos extends RecyclerView.ViewHolder {
        ImageView imagenProducto;
        TextView nombreProducto, valorProducto, pasteleriaProducto;
        RatingBar ratingProducto;

        public ViewHolderDatos(@NonNull View itemView) {
            super(itemView);
            imagenProducto= (ImageView) itemView.findViewById(R.id.imagen_producto);
            nombreProducto= (TextView) itemView.findViewById(R.id.nombre_producto);
            valorProducto= (TextView) itemView.findViewById(R.id.precio_producto);
            pasteleriaProducto= (TextView) itemView.findViewById(R.id.pasteleria_producto);
            ratingProducto= (RatingBar) itemView.findViewById(R.id.rating_producto);
        }
    }
}
