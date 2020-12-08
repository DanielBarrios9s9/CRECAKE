package com.example.creativecake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapCotizaciones extends RecyclerView.Adapter<AdapCotizaciones.CotizacionviewHolder>{
    ArrayList<CotizacionHelperClass> listaProductos;
    Context globalContext;
    String telefono;

    public AdapCotizaciones(ArrayList<CotizacionHelperClass> listaProductos, Context globalContext, String telefono) {
        this.listaProductos = listaProductos;
        this.globalContext = globalContext;
        this.telefono = telefono;
    }

    @NonNull
    @Override
    public CotizacionviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.p_ejemplo_cotizaciones,parent,false);
        return new CotizacionviewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CotizacionviewHolder holder, int position) {
        final CotizacionHelperClass producto = listaProductos.get(position);

        holder.nombreCotizacion.setText(producto.getNombre());
        holder.celularCliente.setText(producto.getNumeroCliente());
        holder.descripcionCotizacion.setText(producto.getEspecificaciones());
        holder.fechaEntrega.setText(producto.getFecha());
        holder.horaEntrega.setText(producto.getHora());
        holder.nombreCliente.setText(producto.getNombreCliente());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Detalles dialog_product = new Detalles(producto, SharedPreferences_Util.getPhone_SP(globalContext), globalContext);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();
    }

    public  class  CotizacionviewHolder extends RecyclerView.ViewHolder {

        CardView card;
        TextView nombreCotizacion, nombreCliente,celularCliente,horaEntrega,fechaEntrega,descripcionCotizacion;

        public CotizacionviewHolder(@NonNull View itemView) {
            super(itemView);

            card =  (CardView) itemView.findViewById(R.id.Card_cotizacion);
            nombreCotizacion= (TextView) itemView.findViewById(R.id.nombre_CotizacionT);
            nombreCliente= (TextView) itemView.findViewById(R.id.nombre_ClienteT);
            celularCliente= (TextView) itemView.findViewById(R.id.celular_ClienteT);
            horaEntrega= (TextView) itemView.findViewById(R.id.hora_PedidoT);
            fechaEntrega = (TextView) itemView.findViewById(R.id.fecha_pedidoT);
            descripcionCotizacion= (TextView) itemView.findViewById(R.id.descripcion_CotizacionT);
        }
    }
}
