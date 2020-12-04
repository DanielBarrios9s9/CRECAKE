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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class VrifPagoClienteFragment extends Fragment {
    private Context globalContext = null;
    NavController navController;
    String telefono, item;
    View v;
    DatabaseReference pago;
    Boolean n, r;
    Query query;
    HelperValor compra;
    CountDownLatch count;
    TextView ITEM;

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

        v=view;
        Verificacion verificar = new Verificacion();
        verificar.execute();
    }

    private class Verificacion extends AsyncTask<Void,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            navController= Navigation.findNavController(v);
            telefono = SharedPreferences_Util.getPhone_SP(globalContext);
            pago= FirebaseDatabase.getInstance().getReference().child("pagoCarrito").child(telefono);
            ITEM= (TextView) v.findViewById(R.id.ITEM);
        }

        @Override
        protected String doInBackground(Void... voids) {
            query = pago.limitToLast(1);
            item=" ";
            n=false;
            r=false;
            while(true){
                count = new CountDownLatch(1);
                publishProgress("1");
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        publishProgress("2");
                        publishProgress("entró al query");
                        for(DataSnapshot snap: snapshot.getChildren()){
                            compra = snap.getValue(HelperValor.class);
                            publishProgress("3");
                            publishProgress("bajó una compra");
                            if(compra.getConfirmacion().equals("ACEPTADO")){
                                publishProgress("4 SI");
                                item=snapshot.getKey();
                                ITEM.setText(item);
                                n=true;
                                r=true;
                                publishProgress("ya fue aceptado");
                                count.countDown();
                                break;
                            }
                            else if(compra.getConfirmacion().equals("DENEGADO")){
                                publishProgress("4 NO");
                                item=snapshot.getKey();
                                ITEM.setText(item);
                                r=true;
                                n=true;
                                publishProgress("ya fue denegado");
                                count.countDown();
                                break;
                            }else{
                                publishProgress("4 Pendiente");
                                count.countDown();
                                break;
                            }
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }});
                try {
                    count.await();
                }catch (InterruptedException in){
                    in.printStackTrace();
                }

                publishProgress("5");
                if(n){break;}
                if(isCancelled()){break;}
                publishProgress("reinicia el loop");
            }

            return item;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            System.out.println(values[0]);
        }

        @Override
        protected void onPostExecute(String resultado) {
            //super.onPostExecute(aBoolean);
            if(resultado.equals(" ")){ navController.navigate(R.id.denegadoClienteFragment);}
            else {navController.navigate(R.id.aceptadoClienteFragment);}
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Toast.makeText(globalContext, "No se ha respondido tu solicitud de compra. Repite el proceso", Toast.LENGTH_SHORT).show();
        }

    }


}