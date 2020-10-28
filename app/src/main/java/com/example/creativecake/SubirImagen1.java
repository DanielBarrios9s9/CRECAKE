package com.example.creativecake;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubirImagen1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubirImagen1 extends Fragment {

    ImageView imagen;
    Button subir, seleccionar;
    EditText nombre, precio, descripcion, cantidad;
    Spinner tipo;
    ProgressDialog cargando;

    private Context globalContext = null;

    StorageReference storageReference;
    FirebaseDatabase database;
    DatabaseReference reference;

    private final int PICK_PHOTO = 1;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SubirImagen1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubirImagen1.
     */
    // TODO: Rename and change types and number of parameters
    public static SubirImagen1 newInstance(String param1, String param2) {
        SubirImagen1 fragment = new SubirImagen1();
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

        globalContext = this.getActivity();
        cargando = new ProgressDialog(globalContext);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View main = inflater.inflate(R.layout.fragment_subir_imagen1, container, false);

        subir = (Button) main.findViewById(R.id.btn_sunirfoto);
        seleccionar = (Button) main.findViewById(R.id.btn_selefoto);
        imagen = (ImageView) main.findViewById(R.id.img_foto);
        nombre = (EditText) getActivity().findViewById(R.id.editNombrenuevoproducto);
        precio = (EditText) getActivity().findViewById(R.id.editPrecionuevoproducto);
        descripcion = (EditText) getActivity().findViewById(R.id.editDescripcionnuevoproducto);
        cantidad = (EditText) getActivity().findViewById(R.id.editCantidad);
        tipo = (Spinner) getActivity().findViewById(R.id.spinnerNuevoProducto);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.Tipo_producto, android.R.layout.simple_spinner_item);

        tipo.setAdapter(adapter);

        seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"),PICK_PHOTO);;
            }
        });
        return  main;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Intent intent1 = getActivity().getIntent();
        final String user_name = intent1.getStringExtra("nombreIngresado");

        if(requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK && data != null && data.getData() != null){

            final Uri filepath = data.getData();


            try{

                Bitmap bitmapImagen = MediaStore.Images.Media.getBitmap(globalContext.getContentResolver(), filepath);
                imagen.setImageBitmap(bitmapImagen);

            } catch (IOException e){
                e.printStackTrace();
            }

            int p = (int) (Math.random() * 25 + 1); int s = (int) (Math.random() * 25 + 1);
            int t = (int) (Math.random() * 25 + 1); int c = (int) (Math.random() * 25 + 1);
            int numero1 = (int) (Math.random() * 1012 + 2111);
            int numero2 = (int) (Math.random() * 1012 + 2111);

            String[] elementos = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
                    "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

            final String aleatorio = elementos[p] + elementos[s] + numero1 + elementos[t] + elementos[c] +
                    numero2;

            subir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cargando.setTitle("Subiendo imagen");
                    cargando.setMessage("Espere por favor...");
                    cargando.show();

                    database = FirebaseDatabase.getInstance();
                    reference = database.getReference("productoTienda");
                    storageReference = FirebaseStorage.getInstance().getReference("Imagen Producto");

                    final StorageReference img_reference = storageReference.child(aleatorio);
                    UploadTask uploadTask = img_reference.putFile(filepath);

                    Navigation.findNavController(v).navigate(R.id.fragment_tienda_inventario);

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            // Continue with the task to get the download URL
                            return img_reference.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();

                                String nombreProducto = nombre.getText().toString();
                                String precioProducto = precio.getText().toString();
                                String descripcionProducto = descripcion.getText().toString();
                                String cantidadProducto = cantidad.getText().toString();
                                String tipoProducto = (String) tipo.getItemAtPosition(tipo.getSelectedItemPosition());

                                final ProductHelperClass helperClass = new ProductHelperClass(nombreProducto, precioProducto
                                        , descripcionProducto, tipoProducto, user_name, downloadUri.toString(), cantidadProducto);

                                reference.push().setValue(helperClass);
                                cargando.dismiss();

                                Toast.makeText(globalContext, "Imagen cargada con exito", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(globalContext, "Imagen cargada sin exito", Toast.LENGTH_SHORT).show();
                                cargando.dismiss();
                            }
                        }
                    });


                }

            });
        }
    }
}