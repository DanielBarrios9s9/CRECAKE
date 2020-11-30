package com.example.creativecake;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorPedido extends RecyclerView.Adapter<AdaptadorPedido.pedidoViewHolder>{

    ArrayList<producto_carrito> listaPedido;
    FragmentManager fragmentManager;
    public AdaptadorPedido(ArrayList<producto_carrito> listaPedido, FragmentManager fragmentManager) {
        this.listaPedido = listaPedido;
        this.fragmentManager = fragmentManager;
    }

    @NonNull
    @Override
    public pedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapatador_pedido,parent,false);
        return new pedidoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull pedidoViewHolder holder, int position) {

        final producto_carrito pedido = listaPedido.get(position);
        holder.nombre_usuario.setText(pedido.getNom_usuario());
        holder.cantidad_carrito.setText(pedido.getCantidad());
        holder.dir_usuario.setText(pedido.getDir_usuario());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putString("nom_user", pedido.getNom_usuario());
                result.putString("dir_user", pedido.getDir_usuario());
                result.putString("cantidad", pedido.getCantidad());
                result.putString("telefono", pedido.getTelefono());
                fragmentManager.setFragmentResult("key", result);
                Navigation.findNavController(v).navigate(R.id.verCarritoDomiciliario);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaPedido.size();
    }

    public  class  pedidoViewHolder extends RecyclerView.ViewHolder {

        CardView card;
        TextView nombre_usuario, dir_usuario, cantidad_carrito;

        public pedidoViewHolder(@NonNull View itemView) {
            super(itemView);

            card =  itemView.findViewById(R.id.card_producto);
            nombre_usuario= (TextView) itemView.findViewById(R.id.Nombre_cliente);
            dir_usuario= (TextView) itemView.findViewById(R.id.Direccion_cliente);
            cantidad_carrito= (TextView) itemView.findViewById(R.id.num_items);

        }
    }
}
