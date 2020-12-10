package com.example.creativecake;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorPedido extends RecyclerView.Adapter<AdaptadorPedido.pedidoViewHolder>{

    ArrayList<producto_carrito> listaPedido;
    FragmentManager fragmentManager;
    boolean qBoton;
    FragmentActivity activity;
    Context context;
    String telefono;

    public AdaptadorPedido(ArrayList<producto_carrito> listaPedido, FragmentManager fragmentManager, boolean qBoton) {
        this.listaPedido = listaPedido;
        this.fragmentManager = fragmentManager;
        this.qBoton = qBoton;
    }

    public AdaptadorPedido(ArrayList<producto_carrito> listaPedido, FragmentManager fragmentManager, boolean qBoton, FragmentActivity activity, Context context, String telefono) {
        this.context = context;
        this.listaPedido = listaPedido;
        this.fragmentManager = fragmentManager;
        this.qBoton = qBoton;
        this.activity = activity;
        this.telefono = telefono;
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
        if(pedido.getCantidad() == null || pedido.getCantidad().equals(" ")){

            holder.cant_car.setText("Tipo:");
            holder.cantidad_carrito.setText("Cotización");

        }else{
            holder.cantidad_carrito.setText(pedido.getCantidad());
        }

        if(!qBoton){

            holder.btn_entrega.setVisibility(View.VISIBLE);

        }else{

            holder.btn_entrega.setVisibility(View.GONE);

        }

        holder.dir_usuario.setText(pedido.getDir_usuario());
        holder.tele_user.setText(pedido.getTelefono());

        holder.btn_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DialogEntregarPeddido dialogEntregarPeddido = new DialogEntregarPeddido(context, telefono, pedido.getKey());

            }
        });


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerCarritoDomiciliario fragment = new VerCarritoDomiciliario(fragmentManager);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                Bundle result = new Bundle();
                result.putString("key_user", pedido.getKey());
                result.putString("nom_user", pedido.getNom_usuario());
                result.putString("dir_user", pedido.getDir_usuario());
                result.putString("cantidad", pedido.getCantidad());
                result.putString("telefono", pedido.getTelefono());
                result.putBoolean("boton", qBoton);
                fragmentManager.setFragmentResult("key", result);
                transaction.replace(R.id.fragment3, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return listaPedido.size();
    }

    public  class  pedidoViewHolder extends RecyclerView.ViewHolder {

        CardView card, card2;
        Button btn_entrega;
        TextView nombre_usuario, dir_usuario, cantidad_carrito, tele_user, cant_car;

        public pedidoViewHolder(@NonNull View itemView) {
            super(itemView);

            btn_entrega = (Button) itemView.findViewById(R.id.btn_entrega);
            card =  itemView.findViewById(R.id.card_producto);
            card2 = itemView.findViewById(R.id.card_btn);
            nombre_usuario= (TextView) itemView.findViewById(R.id.Nombre_cliente);
            dir_usuario= (TextView) itemView.findViewById(R.id.Direccion_cliente);
            cantidad_carrito= (TextView) itemView.findViewById(R.id.num_items);
            cant_car= (TextView) itemView.findViewById(R.id.cantidad_carrito);
            tele_user= (TextView) itemView.findViewById(R.id.tel_user);

        }
    }
}
