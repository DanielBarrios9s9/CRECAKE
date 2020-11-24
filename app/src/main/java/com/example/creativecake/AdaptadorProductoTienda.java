package com.example.creativecake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorProductoTienda extends RecyclerView.Adapter<AdaptadorProductoTienda.ViewHolderDatosTienda> {
    ArrayList<producto_ejemplo> listaProductos;
    Context globalContext;

    public AdaptadorProductoTienda(ArrayList<producto_ejemplo> listaProductos, Context globalContext){
        this.listaProductos = listaProductos;
        this.globalContext = globalContext;
    }

    @Override
    public ViewHolderDatosTienda onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.p_ejemplo_tienda,parent,false);
        return new ViewHolderDatosTienda(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductoTienda.ViewHolderDatosTienda holder, int position) {
        final producto_ejemplo producto = listaProductos.get(position);
        Picasso.get().load(producto.getDownloadUrl()).placeholder(R.drawable.imagenproducto). error(R.drawable.imagenproducto).resize(150,150).into(holder.imagenProducto);
        holder.nombreProducto.setText(producto.getNombre());
        holder.valorProducto.setText("$ "+producto.getPrecio());
        holder.tipoProducto.setText(producto.getTipo());
        holder.ofertaProducto.setText("- "+producto.getOferta()+" %");

        holder.b_ver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_product_tienda dialog_product = new Dialog_product_tienda(globalContext, producto);
            }
        });

        holder.b_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_eliminar dialog_product = new Dialog_eliminar(globalContext, producto);
            }
        });

        holder.b_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_editar dialog_product = new Dialog_editar(globalContext, producto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewHolderDatosTienda extends RecyclerView.ViewHolder {
        ImageView imagenProducto;
        TextView nombreProducto, valorProducto, ofertaProducto, tipoProducto;
        Button b_ver, b_editar, b_eliminar;

        public ViewHolderDatosTienda(@NonNull View itemView) {
            super(itemView);
            imagenProducto= (ImageView) itemView.findViewById(R.id.imagen_producto);
            nombreProducto= (TextView) itemView.findViewById(R.id.nombre_producto);
            valorProducto= (TextView) itemView.findViewById(R.id.precio_producto);
            ofertaProducto= (TextView) itemView.findViewById(R.id.oferta_producto);
            tipoProducto =(TextView) itemView.findViewById(R.id.tipo_product);
            b_editar = (Button) itemView.findViewById(R.id.btn_editar);
            b_eliminar =(Button) itemView.findViewById(R.id.btn_eliminar);
            b_ver = (Button) itemView.findViewById(R.id.btn_ver);
        }
    }
}
