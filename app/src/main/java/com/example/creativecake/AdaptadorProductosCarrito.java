package com.example.creativecake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorProductosCarrito  extends RecyclerView.Adapter<AdaptadorProductosCarrito.ViewHolderDatosCarrito> {
    ArrayList<producto_carrito> listaProductos;
    Context globalContext;
    String telefono;
    int items;

    public AdaptadorProductosCarrito(ArrayList<producto_carrito> listaProductos, Context context, String telefono) {
        this.listaProductos = listaProductos;
        this.globalContext=context;
        this.telefono=telefono;
    }

    @Override
    public ViewHolderDatosCarrito onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.p_ejemplo_carrito,parent,false);
        return new ViewHolderDatosCarrito(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderDatosCarrito holder, int position) {
        final producto_carrito producto = listaProductos.get(position);

        try {
            items= Integer.parseInt(producto.getCantidad());

            Picasso.get().load(producto.getImagen()).placeholder(R.drawable.imagenproducto). error(R.drawable.imagenproducto).resize(150,150).into(holder.imagenProducto);
            holder.nombreProducto.setText(producto.getNombre());
            holder.valorProducto.setText("$ "+producto.getPrecio());
            holder.ofertaProducto.setText("-"+producto.getOferta()+"%");

            holder.mas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items++;
                    DatabaseReference refCantidad = FirebaseDatabase.getInstance().getReference("carrito").child(telefono).child(producto.getLugar()).child("cantidad");
                    refCantidad.setValue(String.valueOf(items));
                    producto.setCantidad(String.valueOf(items));
                }
            });

            holder.menos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    items = Integer.parseInt(producto.getCantidad());
                    if (items>1){
                        items = items-1;
                        DatabaseReference refCantidad = FirebaseDatabase.getInstance().getReference("carrito").child(telefono).child(producto.getLugar()).child("cantidad");
                        refCantidad.setValue(String.valueOf(items));
                        producto.setCantidad(String.valueOf(items));
                    }
                }
            });
            holder.totalItems.setText(producto.getCantidad());

            holder.eliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference refCantidad = FirebaseDatabase.getInstance().getReference("carrito").child(telefono);
                    refCantidad.child(producto.getLugar()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(globalContext, "Se ha eliminado el producto de tu carrito", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(globalContext, "Error al eliminar el producto de tu carrito", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }catch (Exception e){

        }

    }


    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public class ViewHolderDatosCarrito extends RecyclerView.ViewHolder {
        ImageView imagenProducto;
        TextView nombreProducto, valorProducto, totalItems, ofertaProducto;
        ImageButton mas, menos;
        Button eliminar;

        public ViewHolderDatosCarrito(@NonNull View itemView) {
            super(itemView);
            imagenProducto= (ImageView) itemView.findViewById(R.id.imagen_producto);
            nombreProducto= (TextView) itemView.findViewById(R.id.nombre_producto);
            valorProducto= (TextView) itemView.findViewById(R.id.precio_producto);
            totalItems= (TextView) itemView.findViewById(R.id.total_item);
            ofertaProducto =(TextView) itemView.findViewById(R.id.off_producto);
            mas= (ImageButton) itemView.findViewById(R.id.mas);
            menos= (ImageButton) itemView.findViewById(R.id.menos);
            eliminar =(Button) itemView.findViewById(R.id.btn_eliminar);
        }
    }
}
