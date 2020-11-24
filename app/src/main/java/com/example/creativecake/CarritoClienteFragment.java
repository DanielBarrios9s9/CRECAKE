package com.example.creativecake;

import android.content.*;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.*;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.Date;

public class CarritoClienteFragment extends Fragment {
    NavController navController;
    RecyclerView recyclerProductos;
    ArrayList<producto_carrito> listaProductos;
    private Context globalContext = null;
    View v;
    AdaptadorProductosCarrito adapter;
    private DatabaseReference datosCarrito, pagoCa;
    String telefono, item;
    Button finalizarCompra;
    int subTotal, descuento, comision, total;

    public CarritoClienteFragment() {
        // Required empty public constructor
    }

    public static CarritoClienteFragment newInstance(String param1, String param2) {
        CarritoClienteFragment fragment = new CarritoClienteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
        globalContext = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_carrito_cliente,container,false);
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v=view;
        Inicializar();
        Base();
    }

    public void Inicializar(){

        navController= Navigation.findNavController(v);

        Intent intent1 = getActivity().getIntent();
        telefono = intent1.getStringExtra("telefono");

        listaProductos= new ArrayList<>();
        recyclerProductos = v.findViewById(R.id.recyclerviewCarrito);
        recyclerProductos.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter=new AdaptadorProductosCarrito(listaProductos, globalContext, telefono);
        recyclerProductos.setAdapter(adapter);

        finalizarCompra = (Button) v.findViewById(R.id.btn_finalizarCarrito);
    }

    private void Base() {

        datosCarrito= FirebaseDatabase.getInstance().getReference("carrito").child(telefono);
        datosCarrito.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaProductos.removeAll(listaProductos);
                for (DataSnapshot ds: snapshot.getChildren()) {
                    producto_carrito producto = ds.getValue(producto_carrito.class);
                    producto.setLugar(ds.getKey());
                    if (producto.getCantidad()!=" "){
                        listaProductos.add(producto);
                    }
                    else{ break;}
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        finalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comprar();

                pagoCa= FirebaseDatabase.getInstance().getReference("pagoCarrito").child(telefono);
                Query query = pagoCa.limitToLast(1);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds: snapshot.getChildren()) { item = ds.getKey();}
                        pagoCa.child(item).child("subtotal").setValue(String.valueOf(subTotal));
                        pagoCa.child(item).child("descuento").setValue(String.valueOf(descuento));
                        pagoCa.child(item).child("comision").setValue(String.valueOf(comision));
                        pagoCa.child(item).child("valor").setValue(String.valueOf(total)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(globalContext, "Valores guardados", Toast.LENGTH_SHORT).show();
                                navController.navigate(R.id.finCompraClienteFragment);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(globalContext, "Error al guardar los valores", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void Comprar(){
        subTotal = 0;
        descuento = 0;
        comision = 0;
        total=0;

        for (producto_carrito producto:listaProductos) {
            if((producto.getOferta()==" ") || (producto.getOferta()=="0")){
                if(producto.getCantidad()=="1"){
                    try{
                        subTotal = subTotal + Integer.parseInt(producto.getPrecio());
                    }
                    catch (Exception e){
                        Toast.makeText(globalContext, "Fin del listado de productos en el carrito", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                else{
                    try{
                        int items = Integer.parseInt(producto.getCantidad());
                        subTotal = subTotal + (Integer.parseInt(producto.getPrecio())*items);
                    }catch (Exception e){
                        Toast.makeText(globalContext, "Fin del listado de productos en el carrito", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }else{
                if(producto.getCantidad()=="1"){
                    try{
                        int desc = ((Integer.parseInt(producto.getPrecio())*Integer.parseInt(producto.getOferta()))/100);
                        subTotal = subTotal + (Integer.parseInt(producto.getPrecio())-desc);
                        descuento = descuento + desc;
                    } catch (Exception e){
                        Toast.makeText(globalContext, "Fin del listado de productos en el carrito", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                else{
                    try{
                        int items = Integer.parseInt(producto.getCantidad());
                        int desc = (((Integer.parseInt(producto.getPrecio())*items)*Integer.parseInt(producto.getOferta()))/100);
                        subTotal = subTotal + ((Integer.parseInt(producto.getPrecio())*items)-desc);
                        descuento = descuento + desc;
                    }catch (Exception e){
                        Toast.makeText(globalContext, "Fin del listado de productos en el carrito", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }
            }
        }

        comision = ((subTotal*5)/100);
        total= subTotal+comision;
    }
}