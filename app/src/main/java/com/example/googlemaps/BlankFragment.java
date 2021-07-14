package com.example.googlemaps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.File;


public class BlankFragment implements GoogleMap.InfoWindowAdapter {
    Context ctnx;
    public BlankFragment(Context context) {
        this.ctnx = context;
    }
    TextView title;
    TextView informacion;

    @Override
    public View getInfoWindow(Marker marker) {
        View vista = LayoutInflater.from(ctnx).inflate(R.layout.fragment_blank, null);
        title = vista.findViewById(R.id.title);
        informacion = vista.findViewById(R.id.Informacion);
        //-------------------
        title.setText(marker.getTitle());
        informacion.setText(marker.getSnippet());
        //--
        ImageView imagen = (ImageView) vista.findViewById(R.id.imgFacultad);
        imagen.setImageResource(R.drawable.uteq);
            return vista;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}