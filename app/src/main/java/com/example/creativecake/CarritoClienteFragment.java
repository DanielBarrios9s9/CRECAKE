package com.example.creativecake;

import android.content.*;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfDocument.Page;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.net.Uri;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class CarritoClienteFragment extends Fragment {
    NavController navController;
    RecyclerView recyclerProductos;
    ArrayList<Pedido> listaProductos;
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
        telefono = SharedPreferences_Util.getPhone_SP(globalContext);

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
                    Pedido producto = ds.getValue(Pedido.class);
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
                generarPDF();
                /*Comprar();

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
                });*/
            }
        });
    }

    public void Comprar(){
        subTotal = 0;
        descuento = 0;
        comision = 0;
        total=0;

        for (Pedido producto:listaProductos) {
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
    public void generarPDF()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("usuarioCliente");
        Query checkUsuario = reference.orderByChild("telefono").equalTo(SharedPreferences_Util.getPhone_SP(getActivity()));
        checkUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nombre = snapshot.child(SharedPreferences_Util.getPhone_SP(getActivity())).child("nombre").getValue(String.class);
                String telefono = snapshot.child(SharedPreferences_Util.getPhone_SP(getActivity())).child("telefono").getValue(String.class);
                String direccion = snapshot.child(SharedPreferences_Util.getPhone_SP(getActivity())).child("direccion").getValue(String.class);

                PdfDocument factura = new PdfDocument();
                PageInfo facturaInfo = new PageInfo.Builder(350, 400, 1).create();
                Paint paint = new Paint();
                Page paginaPDF = factura.startPage(facturaInfo);
                Canvas canvas = paginaPDF.getCanvas();

                paint.setTextAlign(Paint.Align.CENTER);

                canvas.drawText("CREATIVE CAKE", canvas.getWidth()/2, 15, paint);
                canvas.drawText("",canvas.getWidth()/2, 20, paint);

                paint.setTextSize(8.5f);
                paint.setTextAlign(Paint.Align.LEFT);
                canvas.drawText("Nombre: "+nombre, 20, 30, paint);
                canvas.drawText("Telefono: "+telefono, 20, 50, paint);
                canvas.drawText("Direccion: "+direccion,20,70, paint);
                canvas.drawLine(10,80,340,80,paint);


                /*
                este archivo se guarda en la carpeta android/data/com.example.creativecake/facturas
                no pude cambiarle la ruta :(, si pueden porfa ayudenme con eso
                y paula: le puedes agregar al pdf la informacion del producto a comprar? es con el metodo canvas.drawtext
                 */
                factura.finishPage(paginaPDF);
                File file = new File(getActivity().getExternalFilesDir("../Facturas"), "Factura "+ nombre + ".pdf");


                try {
                    factura.writeTo(new FileOutputStream(file));
                    Toast.makeText(getActivity(), "Factura generada", Toast.LENGTH_SHORT).show();
                    abrirPDF(nombre);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                factura.close();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void abrirPDF(String nombre) {
        File pdfcreado = new File("/Factura " + nombre + ".pdf");
        if(pdfcreado.exists())
        {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfcreado);
            intent.setDataAndType(uri, "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            try
            {
                startActivity(intent);
            }catch (ActivityNotFoundException e)
            {
                Toast.makeText(getActivity(),"No hay aplicación de leer PDFs", Toast.LENGTH_SHORT);
            }
        }
        else
        {
            Toast.makeText(getActivity(),"No se pudo leer PDFs", Toast.LENGTH_SHORT);
        }
    }
}