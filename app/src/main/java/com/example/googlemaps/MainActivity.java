package com.example.googlemaps;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    LatLng[] puntosLatLng;
    PolylineOptions optionsPoly;
    GoogleMap mapa;
    String[] lugares;
    String[] descripciones;
    int con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.contenedor);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //--- Inicializando las variables globales
        this.optionsPoly = new PolylineOptions();
        this.mapa = googleMap;
        this.con = 1;

        this.puntosLatLng = new LatLng[]{
                new LatLng(-1.012555490518025, -79.46846440674328), //Biblioteca UTEQ
                new LatLng(-1.0813875510502302, -79.50266857308435), //Finca La Maria pecuarias
                new LatLng(-1.0126437796746306, -79.47025158695742)};  //FCI UTEQ

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.setInfoWindowAdapter(new BlankFragment(MainActivity.this));
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                marcarPunto(latLng);
              }

        });
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                Toast.makeText(MainActivity.this,marker.getTitle(),Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        CameraUpdate camara = CameraUpdateFactory.newLatLngZoom(this.puntosLatLng[2], 6);
        googleMap.moveCamera(camara);
    }

    public void marcarPunto(LatLng punto){
        this.mapa.addMarker(new MarkerOptions().position(punto).title("Punto " + this.con++));
        this.optionsPoly.add(punto);
    }

    public void btnLocalizarCamara(View view){

        this.lugares = new String[]{"Biblioteca UTEQ", "Facultad de Ciencias Pecuarias", "Facultad Ciencias de la Ingeniería"};

        new MaterialAlertDialogBuilder(this)
                .setTitle("LUGARES")
                .setItems(lugares, (dialog, which) -> {
                    cambiarLugar(which);
                }).show();
    }
    public void cambiarLugar(int lugar){
         this.descripciones = new String[]{"Encargado: Victo Pineira \n"+"Direccion: Av. Quito km. 1 1/2 vía a Santo Domingo de los Tsáchilas",
                                           "Decano: Ing. Jenny Guiselli Torres Navarrete\n"+"Direccion:km 7 vía Quevedo-El Empalme",
                                           "Decano:Ing. Washington Alberto Chiriboga Casanova\n"+"Direccion: Av. Quito km. 1 1/2 vía a Santo Domingo de los Tsáchilas"};
            int zoom = 20;
        Eliminar(null);
        CameraPosition posiCamara = new CameraPosition.Builder()
                .target(this.puntosLatLng[lugar])
                .zoom(zoom)
                .bearing(45)
                .tilt(50)
                .build();
        CameraUpdate camara = CameraUpdateFactory.newCameraPosition(posiCamara);
        mapa.animateCamera(camara);

        this.mapa.addMarker(new MarkerOptions().position(this.puntosLatLng[lugar]).title((lugares[lugar]).toString()).snippet(descripciones[lugar]));

    }

    public void Eliminar(View view){
        this.con = 0;
        this.mapa.clear();
        this.optionsPoly = new PolylineOptions();
    }
}