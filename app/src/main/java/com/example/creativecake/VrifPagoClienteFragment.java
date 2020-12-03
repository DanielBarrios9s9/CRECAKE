package com.example.creativecake;

import android.content.Context;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class VrifPagoClienteFragment extends Fragment {
    private Context globalContext = null;
    NavController navController;
    String telefono, item;
    DatabaseReference pago, domicilios, ventas, carrito;
    Boolean n, r;
    Query query;
    HelperValor compra;
    List<ItemHelperClass> listadoCompras;

    public VrifPagoClienteFragment() {
        // Required empty public constructor
    }

    public static VrifPagoClienteFragment newInstance(String param1, String param2) {
        VrifPagoClienteFragment fragment = new VrifPagoClienteFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vrif_pago_cliente, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController= Navigation.findNavController(view);
        Verificacion verificar = new Verificacion();
        verificar.execute();
    }

    private class Verificacion extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listadoCompras= new LinkedList<>();

            telefono = SharedPreferences_Util.getPhone_SP(globalContext);


            pago= FirebaseDatabase.getInstance().getReference().child("pagoCarrito").child(telefono);
            domicilios=FirebaseDatabase.getInstance().getReference().child("domicilios");
            ventas = FirebaseDatabase.getInstance().getReference().child("Ventas");
            carrito = FirebaseDatabase.getInstance().getReference().child("carrito").child(telefono);
            n=false;
            r=false;
            query = pago.limitToLast(1);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {

            while(true){
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap: snapshot.getChildren()){compra = snap.getValue(HelperValor.class);}
                        if(compra.getConfirmacion().equals("ACEPTADO")){
                            item=snapshot.getKey();
                            n=true;
                            r=true;
                            carrito.addListenerForSingleValueEvent(new ValueEventListener() {
                                @RequiresApi(api = Build.VERSION_CODES.O)
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    listadoCompras.removeAll(listadoCompras);
                                    for(DataSnapshot snap: snapshot.getChildren()){
                                        ItemHelperClass producto = snap.getValue(ItemHelperClass.class);
                                        producto.setFecha(LocalDate.now().toString());
                                        producto.setHora(LocalTime.now().toString());
                                        listadoCompras.add(producto);
                                    }
                                    domicilios.push().setValue(listadoCompras).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(globalContext, "Productos listos para asignar a domiciliario", Toast.LENGTH_SHORT).show();
                                            carrito.removeValue();
                                            carrito.child(telefono).child("1").setValue(" ").addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(globalContext, "Carrito listo para nueva compra", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(globalContext, "Error al vaciar el carrito", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(globalContext, "Error al agregar los productos a domicilios", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            int numItem = Integer.parseInt(item) + 1;
                            String newItem = String.valueOf(numItem);
                            pago.child(newItem).setValue(" ");
                        }
                        else if(compra.getConfirmacion().equals("DENEGADO")){
                            r=true;
                            n=true;
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                if(n){ break;}
                if(isCancelled()){break;}
            }

            Boolean s = false;
            if(n && r){s=true;}
            else if (n && (!r)){s=false;}

            return s;
        }

        @Override
        protected void onPostExecute(Boolean resultado) {
            //super.onPostExecute(aBoolean);
            if(resultado){navController.navigate(R.id.denegadoClienteFragment);
            }else {navController.navigate(R.id.aceptadoClienteFragment);}
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(globalContext, "No se ha respondido tu solicitud de compra. Repite el proceso", Toast.LENGTH_SHORT).show();
        }
    }


}