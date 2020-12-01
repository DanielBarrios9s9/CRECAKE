package com.example.creativecake;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UbicacionDomiciliario extends Fragment implements OnMapReadyCallback {

    GoogleMap mMap;
    MapView map_v;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBudleKey";
    private Context globalContext = null;

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
        View v =  inflater.inflate(R.layout.fragment_ubicacion_domiciliario, container, false);
        map_v = (MapView) v.findViewById(R.id.mapa);
        Bundle mapViewBundle = null;

        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        map_v.onCreate(mapViewBundle);
        map_v.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(GoogleMap map){

        map.addMarker(new MarkerOptions()
        .position(new LatLng(0, 0))
        .title("Marker"));


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
}