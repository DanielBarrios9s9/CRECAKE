package com.example.creativecake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorProductoCatalogo extends RecyclerView.Adapter<AdaptadorProductoCatalogo.CatalogoviewHolder>{
    ArrayList<producto_ejemplo> listaProductos;
    Context globalContext;
    String telefono;

    public AdaptadorProductoCatalogo(ArrayList<producto_ejemplo> listaProductos, Context globalContext, String telefono) {
        this.listaProductos = listaProductos;
        this.globalContext = globalContext;
        this.telefono=telefono;
        System.out.println(telefono);
    }

    @NonNull
    @Override
    public CatalogoviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.p_ejemplo_cat,parent,false);
        return new CatalogoviewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogoviewHolder holder, int position) {
        final producto_ejemplo producto = listaProductos.get(position);

        Picasso.get().load(producto.getDownloadUrl()).placeholder(R.drawable.imagenproducto). error(R.drawable.imagenproducto).resize(150,150).into(holder.imagenProducto);
        holder.nombreProducto.setText(producto.getNombre());
        holder.valorProducto.setText("$ "+producto.getPrecio());
        holder.pasteleriaProducto.setText("Pastelería "+producto.getUser_name());
        holder.tipoProducto.setText(producto.getTipo());
        holder.ratingProducto.setRating(Float.parseFloat(producto.getRating()));
        holder.ofertaProducto.setText("- "+producto.getOferta()+" %");



        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_product dialog_product = new Dialog_product(globalContext, producto, SharedPreferences_Util.getPhone_SP(globalContext));
            }
        });

        holder.card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_product dialog_product = new Dialog_product(globalContext, producto, SharedPreferences_Util.getPhone_SP(globalContext));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public  class  CatalogoviewHolder extends RecyclerView.ViewHolder {

        CardView card, card2;
        ImageView imagenProducto;
        TextView nombreProducto, valorProducto, pasteleriaProducto, tipoProducto, ofertaProducto;
        RatingBar ratingProducto;

        public CatalogoviewHolder(@NonNull View itemView) {
            super(itemView);

            card =  (CardView) itemView.findViewById(R.id.card_item);
            card2 =  (CardView) itemView.findViewById(R.id.card_producto);
            imagenProducto= (ImageView) itemView.findViewById(R.id.imagen_producto);
            nombreProducto= (TextView) itemView.findViewById(R.id.nombre_producto);
            valorProducto= (TextView) itemView.findViewById(R.id.precio_producto);
            pasteleriaProducto= (TextView) itemView.findViewById(R.id.pasteleria_producto);
            tipoProducto= (TextView) itemView.findViewById(R.id.tipo_producto);
            ofertaProducto = (TextView) itemView.findViewById(R.id.descuento_producto);
            ratingProducto= (RatingBar) itemView.findViewById(R.id.rating_producto);
        }
    }

}
