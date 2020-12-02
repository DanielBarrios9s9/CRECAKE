package com.example.creativecake;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class AceptadoClienteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button botonFactura;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AceptadoClienteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AceptadoClienteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AceptadoClienteFragment newInstance(String param1, String param2) {
        AceptadoClienteFragment fragment = new AceptadoClienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_aceptado_cliente, container, false);

        botonFactura = (Button)view.findViewById(R.id.descargar_factura);

        botonFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generarPDF();
            }
        });
        return view;
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
}