package com.example.creativecake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorCarritoDomiciliario extends RecyclerView.Adapter<AdaptadorCarritoDomiciliario.pedidoviewHolder>{

    ArrayList<Pedido> listaProductos;
    Context globalContext;

    public AdaptadorCarritoDomiciliario(ArrayList<Pedido> listaProductos, Context globalContext) {
        this.listaProductos = listaProductos;
        this.globalContext = globalContext;

    }

    @NonNull
    @Override
    public pedidoviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.adaptador_ver_carrito_domiciliario,parent,false);
        return new pedidoviewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull pedidoviewHolder holder, int position) {
        final Pedido producto = listaProductos.get(position);
        Picasso.get().load(producto.getImagen()).placeholder(R.drawable.imagenproducto). error(R.drawable.imagenproducto).resize(150,150).into(holder.imagenProducto);
        holder.nombreProducto.setText(producto.getProducto());
        holder.valorProducto.setText("$ "+producto.getPrecio());
        holder.pasteleriaProducto.setText(producto.getTienda());
        holder.cantidadProducto.setText("Cantida: " + producto.getCantidad());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_ProductoDomiciliario dialog_product = new Dialog_ProductoDomiciliario(globalContext, producto);
            }
        });

        holder.card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_ProductoDomiciliario dialog_product = new Dialog_ProductoDomiciliario(globalContext, producto);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public  class  pedidoviewHolder extends RecyclerView.ViewHolder {

        CardView card, card2;
        ImageView imagenProducto;
        TextView nombreProducto, valorProducto, pasteleriaProducto, cantidadProducto;

        public pedidoviewHolder(@NonNull View itemView) {
            super(itemView);

            card =  (CardView) itemView.findViewById(R.id.card_item);
            card2 =  (CardView) itemView.findViewById(R.id.card_producto);
            imagenProducto= (ImageView) itemView.findViewById(R.id.imagen_producto);
            nombreProducto= (TextView) itemView.findViewById(R.id.nombre_producto);
            valorProducto= (TextView) itemView.findViewById(R.id.precio_producto);
            pasteleriaProducto= (TextView) itemView.findViewById(R.id.pasteleria_producto);
            cantidadProducto = (TextView) itemView.findViewById(R.id.cantidad_producto);
        }
    }
}
