package com.example.creativecake;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapatadorDireccionesUser extends RecyclerView.Adapter<AdapatadorDireccionesUser.pedidoViewHolder>{

    ArrayList<String> nom_user;
    ArrayList<String> dir_user;
    Context context;
    ClipboardManager clipboardManager;

    public AdapatadorDireccionesUser(ArrayList<String> nom_user, ArrayList<String> dir_user, Context context){

        clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        this.context = context;
        this.nom_user = nom_user;
        this.dir_user = dir_user;

    }

    @NonNull
    @Override
    public pedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapatador_dir_users,parent,false);
        return new pedidoViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull pedidoViewHolder holder, int position) {

        String nombre = nom_user.get(position);
        String direccion = dir_user.get(position);
        holder.nombre_usuario.setText(nombre);
        holder.dir_usuario.setText(direccion);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClipData clip = ClipData.newPlainText("simple text", holder.dir_usuario.getText());
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(context, "Dirección copiada en el portapapeles", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return dir_user.size();
    }

    public  class  pedidoViewHolder extends RecyclerView.ViewHolder {

        TextView nombre_usuario, dir_usuario;
        CardView card;

        public pedidoViewHolder(@NonNull View itemView) {
            super(itemView);

            card = (CardView) itemView.findViewById(R.id.card_producto);
            nombre_usuario= (TextView) itemView.findViewById(R.id.Nombre_cliente);
            dir_usuario= (TextView) itemView.findViewById(R.id.Direccion_cliente);

        }
    }
}
