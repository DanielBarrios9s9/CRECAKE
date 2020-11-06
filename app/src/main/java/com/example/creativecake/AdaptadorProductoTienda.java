package com.example.creativecake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorProductoTienda extends RecyclerView.Adapter<AdaptadorProductoTienda.ViewHolderDatosTienda> {
    ArrayList<p_ejemplo_tienda> listaProductos;

    public AdaptadorProductoTienda( ArrayList<p_ejemplo_tienda> listaProductos){
        this.listaProductos = listaProductos;
    }

    @Override
    public ViewHolderDatosTienda onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.p_ejemplo_tienda,null,false);
        return new ViewHolderDatosTienda(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductoTienda.ViewHolderDatosTienda holder, int position) {
        //holder.imagenProducto.setImageResource(listaProductos.get(position).getImagenProducto());
        holder.nombreProducto.setText(listaProductos.get(position).getNombreProducto());
        holder.valorProducto.setText(listaProductos.get(position).getValorProducto());
        holder.ofertaProducto.setText(listaProductos.get(position).getOfertaProducto());
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewHolderDatosTienda extends RecyclerView.ViewHolder {
        ImageView imagenProducto;
        TextView nombreProducto, valorProducto, ofertaProducto;

        public ViewHolderDatosTienda(@NonNull View itemView) {
            super(itemView);
            imagenProducto= (ImageView) itemView.findViewById(R.id.imagen_producto);
            nombreProducto= (TextView) itemView.findViewById(R.id.nombre);
            valorProducto= (TextView) itemView.findViewById(R.id.precio_producto);
            ofertaProducto= (TextView) itemView.findViewById(R.id.oferta_producto);
        }
    }
}
