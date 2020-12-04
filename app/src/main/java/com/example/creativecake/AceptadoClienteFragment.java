package com.example.creativecake;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class AceptadoClienteFragment extends Fragment {
    private Button botonFactura, botonFin;
    private NavController navController;
    private Context globalContext;
    String telefono;
    DatabaseReference pago,domicilios, ventas, carrito;
    List<ItemHelperClass> listadoCompras;
    CountDownLatch count;
    Boolean n;
    TextView ITEM;
    int i;
    ProgressBar progressBar;
    View v;

    public AceptadoClienteFragment() {
        // Required empty public constructor
    }

    public static AceptadoClienteFragment newInstance(String param1, String param2) {
        AceptadoClienteFragment fragment = new AceptadoClienteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        globalContext=this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aceptado_cliente, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view;

        botonFactura = (Button)view.findViewById(R.id.descargar_factura);
        botonFin = (Button)view.findViewById(R.id.btn_fin);
        progressBar =(ProgressBar) view.findViewById(R.id.progressBar2);

        botonFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarPDF();
            }
        });

        botonFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reordenar verificar = new Reordenar();
                verificar.execute();

            }
        });

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
                PdfDocument.PageInfo facturaInfo = new PdfDocument.PageInfo.Builder(350, 400, 1).create();
                Paint paint = new Paint();
                PdfDocument.Page paginaPDF = factura.startPage(facturaInfo);
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

    private class Reordenar extends AsyncTask<Void,Integer,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            navController= Navigation.findNavController(v);
            listadoCompras= new LinkedList<>();
            telefono = SharedPreferences_Util.getPhone_SP(globalContext);

            pago= FirebaseDatabase.getInstance().getReference().child("pagoCarrito").child(telefono);
            domicilios=FirebaseDatabase.getInstance().getReference().child("domicilios");
            ventas = FirebaseDatabase.getInstance().getReference().child("Ventas");
            carrito = FirebaseDatabase.getInstance().getReference().child("carrito");

            ITEM= (TextView) getActivity().findViewById(R.id.ITEM);
            n=false;
            botonFin.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(100);
            progressBar.setProgress(0);
            i=0;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            Toast.makeText(globalContext, "¡Pronto el domiciliario recogerá tu pedido y lo llevará hasta tu puerta!", Toast.LENGTH_SHORT).show();
            carrito.child(telefono).child("1").setValue(" ").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressBar.setProgress(100);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            progressBar.setVisibility(View.INVISIBLE);
            botonFin.setEnabled(true);
            navController.navigate(R.id.inicioClienteFragment);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0].intValue());
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            count = new CountDownLatch(3);
            carrito.child(telefono).addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listadoCompras.removeAll(listadoCompras);
                    for(DataSnapshot snap: snapshot.getChildren()){
                        if (!snap.getValue().equals(" ")){
                            ItemHelperClass producto = snap.getValue(ItemHelperClass.class);
                            producto.setFecha(LocalDate.now().toString());
                            producto.setHora(LocalTime.now().toString());
                            ventas.child(producto.getNumeroTienda()).push().setValue(producto).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    i+=2;
                                    publishProgress(i);
                                }
                            });
                            listadoCompras.add(producto);
                        }
                        else{ break;}
                    }
                    count.countDown();
                    domicilios.push().setValue(listadoCompras).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            i+=2;
                            publishProgress(i);
                            count.countDown();
                            carrito.child(telefono).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    i+=2;
                                    publishProgress(i);
                                    count.countDown();
                                }
                            });

                        }
                    });
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {}});
            try {
                count.await();
            }catch (InterruptedException in){
                in.printStackTrace();
            }
            publishProgress(90);
            return true;
        }
    }
}