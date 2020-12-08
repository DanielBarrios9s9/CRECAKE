package com.example.creativecake;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class RevisarCotiClienteFragment extends Fragment {
    DatabaseReference pagoCa, pagoCarrito;
    String telefono, item,subTotal, comision, total;
    TextView tienda, numero, direccion, nombre, fecha, hora;
    private Context globalContext = null;


    public RevisarCotiClienteFragment() {
        // Required empty public constructor
    }

    public static RevisarCotiClienteFragment newInstance(String param1, String param2) {
        RevisarCotiClienteFragment fragment = new RevisarCotiClienteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        globalContext = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_revisar_coti_cliente, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        telefono = SharedPreferences_Util.getPhone_SP(globalContext);

        tienda= (TextView) view.findViewById(R.id.nomTiemdaCli);
        numero=(TextView) view.findViewById(R.id.telTiendaCLi);
        direccion=(TextView) view.findViewById(R.id.direccionTiendaCli);
        nombre=(TextView) view.findViewById(R.id.nombreCotiCli) ;
        fecha= (TextView) view.findViewById(R.id.fechaEntregaCli);
        hora=(TextView)view.findViewById(R.id.horaEntregaCli);

        Button btn_finalizarCompra = view.findViewById(R.id.btn_finalizarCompra);

        final NavController navController= Navigation.findNavController(view);
//Llenado de los textView------------------------------------------------
        pagoCa= FirebaseDatabase.getInstance().getReference().child("chat").child(telefono);
        pagoCa.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.getValue().equals(" ")){
                    ClienteCotiHelper compraA = snapshot.getValue(ClienteCotiHelper.class);
                    subTotal=compraA.getPrecio();
                    int comi=Integer.parseInt(compraA.getPrecio());
                    int too= (5*100)/comi;
                    comision=String.valueOf(too);
                    int tota= comi+too;
                    total= String.valueOf(tota);

                    tienda.setText(compraA.getTienda());
                    numero.setText(compraA.getNumeroTienda());
                    direccion.setText(compraA.getDireccionTienda());
                    nombre.setText(compraA.getNombreCotizacion());
                    fecha.setText(compraA.getFechaEntrega());
                    hora.setText(compraA.getHoraEntrega());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//--------------------------------------------------------------------
        btn_finalizarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagoCarrito= FirebaseDatabase.getInstance().getReference().child("pagoCarrito").child(telefono);
                Query query = pagoCarrito.limitToLast(1);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot snap: snapshot.getChildren()){
                            item = snap.getKey();
                            if (!snap.getValue().equals(" ")){
                                HelperValor compraA = snap.getValue(HelperValor.class);
                                if(compraA.getConfirmacion().equals("ACEPTADO")){
                                    int numItem = Integer.parseInt(item) + 1;
                                    String newItem = String.valueOf(numItem);
                                    pagoCarrito.child(newItem).setValue(" ");
                                    HelperValor compra = new HelperValor(total,subTotal,"0",comision,"PENDIENTE"," "," ");
                                    pagoCarrito.child(newItem).setValue(compra).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                }else{
                                    HelperValor compra = new HelperValor(total,subTotal,"0",comision,"PENDIENTE"," "," ");
                                    pagoCarrito.child(item).setValue(compra).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                    break;
                                }
                            }
                            else{
                                HelperValor compra = new HelperValor(total,subTotal,"0",comision,"PENDIENTE"," "," ");
                                pagoCarrito.child(item).setValue(compra).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                                break;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }


}