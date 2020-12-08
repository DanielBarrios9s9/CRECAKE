package com.example.creativecake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorPedidosTienda extends RecyclerView.Adapter<AdaptadorPedidosTienda.PedidosviewHolder>{
    ArrayList<ItemHelperClass> listaProductos;
    Context globalContext;
    String telefono;

    public AdaptadorPedidosTienda(ArrayList<ItemHelperClass> listaProductos, Context globalContext, String telefono) {
        this.listaProductos = listaProductos;
        this.globalContext = globalContext;
        this.telefono = telefono;
    }

    @NonNull
    @Override
    public PedidosviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.p_ejemplo_pedidos,parent,false);
        return new PedidosviewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosviewHolder holder, int position) {
        final ItemHelperClass producto = listaProductos.get(position);

        Picasso.get().load(producto.getImagen()).placeholder(R.drawable.imagenproducto). error(R.drawable.imagenproducto).resize(100,100).into(holder.imagenProducto);
        holder.nombreProducto.setText(producto.getProducto());
        holder.cantidadProducto.setText("x "+producto.getCantidad());
        holder.fechaEntrega.setText(producto.getFecha());
        holder.nombreCliente.setText("Cliente: "+producto.getNumeroUsuario());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_entregado dialog_product = new Dialog_entregado(globalContext,producto, telefono);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public  class  PedidosviewHolder extends RecyclerView.ViewHolder {

        CardView card;
        ImageView imagenProducto;
        TextView nombreProducto, cantidadProducto, fechaEntrega, nombreCliente;

        public PedidosviewHolder(@NonNull View itemView) {
            super(itemView);

            card =  (CardView) itemView.findViewById(R.id.Card_Pedido);
            imagenProducto= (ImageView) itemView.findViewById(R.id.imagen_Pedido);
            nombreProducto= (TextView) itemView.findViewById(R.id.Nombre_Pedido);
            cantidadProducto= (TextView) itemView.findViewById(R.id.Cantidad_Pedido);
            fechaEntrega= (TextView) itemView.findViewById(R.id.Fecha_PedidoHoy);
            nombreCliente = (TextView) itemView.findViewById(R.id.Nombre_Cli_P);
        }
    }
}
