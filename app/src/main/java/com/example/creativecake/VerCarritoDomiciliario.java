package com.example.creativecake;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.EventListener;

public class VerCarritoDomiciliario extends Fragment {

    String item;
    View v;
    TextView nombre_user;
    Button agregar_carrito, cancelar_carrito;
    AdaptadorCarritoDomiciliario adapterDomiciliario;
    private RecyclerView recycler_pedidos;
    private DatabaseReference pedidosCarrito;
    private DatabaseReference pedidosAgregados;
    private ArrayList<ItemHelperClass> listaDel_carrito;
    private ArrayList<ItemHelperClass> lista_agregados;
    private Context globalContext = null;
    int count = 0;
    FragmentManager fragmentManager;

    public VerCarritoDomiciliario() {
        // Required empty public constructor
    }

    public VerCarritoDomiciliario(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalContext = this.getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ver_carrito_domiciliario, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.v =view;

        inicializar();
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull final Bundle result) {

                nombre_user.setText(result.getString("nom_user"));

                if(result.getBoolean("boton")){
                    baseCarrito(result.getString("key_user"));
                    agregar_carrito.setVisibility(View.VISIBLE);
                    cancelar_carrito.setVisibility(View.INVISIBLE);
                }else{
                    basePedidoAgregado(result.getString("key_user"));
                    cancelar_carrito.setVisibility(View.VISIBLE);
                    agregar_carrito.setVisibility(View.INVISIBLE);
                }

                agregar_carrito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        agregarPedido(result.getString("key_user"), listaDel_carrito);
                        InicioDomiciliarioFragment fragment = new InicioDomiciliarioFragment();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment3, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                });

                cancelar_carrito.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelarCarrito(result.getString("key_user"), listaDel_carrito);
                        pedidosAceptadosDomiciliario fragment2 = new pedidosAceptadosDomiciliario();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment3, fragment2);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                });

            }
        });
    }

    public void inicializar() {

        agregar_carrito = (Button) v.findViewById(R.id.btn_add);
        cancelar_carrito = (Button) v.findViewById(R.id.btn_cancel);
        nombre_user = (TextView) v.findViewById(R.id.nom_user);
        recycler_pedidos = (RecyclerView) v.findViewById(R.id.recyclerPedido);
        recycler_pedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        listaDel_carrito = new ArrayList<>();
        adapterDomiciliario = new AdaptadorCarritoDomiciliario(listaDel_carrito, globalContext);
        recycler_pedidos.setAdapter(adapterDomiciliario);
    }

    private void agregarPedido(final String telefono, final ArrayList<ItemHelperClass> lista){
        DatabaseReference pedidoAgregado = FirebaseDatabase.getInstance().getReference().child("pedidoAgregadoDomiciliario").child(SharedPreferences_Util.getPhone_SP(globalContext)).child(telefono);
            System.out.println("Entro al for " + count);
            count = 0;
            Query query = pedidoAgregado.limitToLast(1);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for( ItemHelperClass producto: lista) {
                        if (!producto.getImagen().equals("Ninguna")) {
                            String contar = String.valueOf(count);
                            pedidoAgregado.child(contar).setValue(producto).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    DatabaseReference pedidoBorrado = FirebaseDatabase.getInstance().getReference().child("domicilios").child(telefono);
                                    pedidoBorrado.getRef().removeValue();
                                }
                            });

                            count++;

                        }else{

                            pedidoAgregado.setValue(producto).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    DatabaseReference pedidoBorrado = FirebaseDatabase.getInstance().getReference().child("domicilios").child(telefono);
                                    pedidoBorrado.getRef().removeValue();
                                }
                            });
                        }
                    }
                    Toast.makeText(globalContext, "Pedido tomado exitosamente", Toast.LENGTH_SHORT).show();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(globalContext, "Error al tomar pedido", Toast.LENGTH_SHORT).show();

                }
            });

    }

    private void cancelarCarrito(final String telefono, ArrayList<ItemHelperClass> lista){
        DatabaseReference pedidoCancelado = FirebaseDatabase.getInstance().getReference().child("domicilios").child(telefono);
        pedidoCancelado.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (ItemHelperClass product : lista) {
                    if(!product.getImagen().equals("Ninguna")) {
                        String contar = String.valueOf(count);
                        pedidoCancelado.child(contar).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                DatabaseReference pedido = FirebaseDatabase.getInstance().getReference().child("pedidoAgregadoDomiciliario").child(SharedPreferences_Util.getPhone_SP(globalContext)).child(telefono);
                                pedido.getRef().removeValue();
                            }
                        });
                        count++;
                    }else{

                        pedidoCancelado.setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                DatabaseReference pedidoBorrado = FirebaseDatabase.getInstance().getReference().child("pedidoAgregadoDomiciliario").child(SharedPreferences_Util.getPhone_SP(globalContext)).child(telefono);
                                pedidoBorrado.getRef().removeValue();
                            }
                        });
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void baseCarrito(String telefono){

        pedidosAgregados= FirebaseDatabase.getInstance().getReference().getRoot().child("domicilios").child(telefono);
        pedidosAgregados.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDel_carrito.removeAll(listaDel_carrito);
                if(snapshot.hasChild("0")) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (!ds.getValue().equals(" ") && !ds.getValue().equals(null)) {
                            if (!ds.child("imagen").getValue().equals(" ")) {
                                ItemHelperClass producto = ds.getValue(ItemHelperClass.class);
                                listaDel_carrito.add(producto);
                            }
                        }
                    }
                }else{

                    ItemHelperClass producto = snapshot.getValue(ItemHelperClass.class);
                    listaDel_carrito.add(producto);

                }
                adapterDomiciliario.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void basePedidoAgregado(String telefono){

        pedidosCarrito= FirebaseDatabase.getInstance().getReference().getRoot().child("pedidoAgregadoDomiciliario").child(SharedPreferences_Util.getPhone_SP(globalContext)).child(telefono);
        Query query = pedidosCarrito.orderByChild("tienda");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDel_carrito.removeAll(listaDel_carrito);
                if(snapshot.hasChild("0")) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if (!ds.getValue().equals(" ") && !ds.getValue().equals(null)) {
                            if (!ds.child("imagen").getValue().equals(" ")) {
                                ItemHelperClass producto = ds.getValue(ItemHelperClass.class);
                                listaDel_carrito.add(producto);
                            }
                        }
                    }
                }else{

                    ItemHelperClass producto = snapshot.getValue(ItemHelperClass.class);
                    listaDel_carrito.add(producto);

                }
                adapterDomiciliario.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}