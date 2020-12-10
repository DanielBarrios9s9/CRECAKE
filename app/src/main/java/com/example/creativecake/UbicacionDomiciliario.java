package com.example.creativecake;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class UbicacionDomiciliario extends Fragment implements OnMapReadyCallback {

    static int REQUEST_CHECK_SETTINGS = 1;
    boolean requestingLocationUpdates = true;
    Location mCurrentLocation;
    LocationCallback locationCallback = new LocationCallback();
    GoogleMap mMap;
    LatLng myUbication;
    MapView map_v;
    double[] coor = new double[2];
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBudleKey";
    private Context globalContext = null;
    private FusedLocationProviderClient fusedLocationProviderClient;
    DatabaseReference ubication;

    public UbicacionDomiciliario() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globalContext = this.getActivity();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ubicacion_domiciliario, container, false);
        map_v = (MapView) v.findViewById(R.id.mapa);
        Bundle mapViewBundle = null;


        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        map_v.onCreate(mapViewBundle);
        map_v.getMapAsync(this);


        crearLocationRequest();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(globalContext);
        obtenerLocation();

        return v;
    }

    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;
        int estado_permiso = ContextCompat.checkSelfPermission(globalContext, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (estado_permiso == PackageManager.PERMISSION_GRANTED) {

        }

        retornarUbicacion(mMap);


    }

    public void Antut(GoogleMap googleMap){
        mMap = googleMap;
        final LatLng punto1 = new LatLng(coor[0], coor[1]);
        mMap.addMarker(new MarkerOptions().position(punto1).title("Hola").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }


    @Override
    public void onStart() {

        super.onStart();
        map_v.onStart();
        
    }

    @Override
    public void onResume() {
        super.onResume();
        map_v.onResume();
        if(requestingLocationUpdates){

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        map_v.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        map_v.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        map_v.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if(mapViewBundle == null){
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }
        map_v.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map_v.onLowMemory();
    }

    public void obtenerLocation(){
        int estado_permiso = ContextCompat.checkSelfPermission(globalContext, Manifest.permission.ACCESS_FINE_LOCATION);
        if(estado_permiso == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                subirUbicacion(location.getLatitude(), location.getLongitude());

                            }
                        }
                    });

        }

    }

    private void subirUbicacion(double latitud, double longitud){
        ubication = FirebaseDatabase.getInstance().getReference("ubicacionDomiciliario").child(SharedPreferences_Util.getPhone_SP(globalContext));
        ubication.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ubication.child("Latitud").setValue(latitud);
                ubication.child("Longitud").setValue(longitud);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void retornarUbicacion(GoogleMap googleMap){
        mMap = googleMap;
        DatabaseReference retorno = FirebaseDatabase.getInstance().getReference("ubicacionDomiciliario").child(SharedPreferences_Util.getPhone_SP(globalContext));
        retorno.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LatLng punto1 = new LatLng((double) snapshot.child("Latitud").getValue(), (double) snapshot.child("Longitud").getValue());
                mMap.addMarker(new MarkerOptions().position(punto1).title("Hola").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    protected void crearLocationRequest() {

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(globalContext);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
            }
        });
        task.addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    try {

                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                    } catch (IntentSender.SendIntentException sendEx) {

                    }
                }
            }
        });
        if (ActivityCompat.checkSelfPermission(globalContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(globalContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }


    }

}