package com.example.osm_family;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configurar osmdroid
        Configuration.getInstance().load(
                this, getSharedPreferences("osmdroid_prefs", MODE_PRIVATE)
        );

        setContentView(R.layout.activity_main);

        // Referencia al MapView
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        // Lista de familiares con nombres y coordenadas
        ArrayList<Familiar> familiares = new ArrayList<>();
        familiares.add(new Familiar("Papá", -17.85449, -63.16202));
        familiares.add(new Familiar("Mamá", -17.85450, -63.16202));     // pequeña variación
        familiares.add(new Familiar("Hermano", -17.85451, -63.16202));   // pequeña variación
        familiares.add(new Familiar("Tío", -17.8504125, -63.1553205));   // centro del rectángulo de OSM
        familiares.add(new Familiar("Hermana", -16.492211, -68.162935)); // nueva agregada

// Agregar marcadores al mapa
        for (Familiar f : familiares) {
            Marker marker = new Marker(map);
            marker.setPosition(new GeoPoint(f.lat, f.lon));
            marker.setTitle(f.nombre);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(marker);
        }

// Centrar mapa en el primer familiar (Papá)
        map.getController().setZoom(16.0);
        map.getController().setCenter(new GeoPoint(familiares.get(0).lat, familiares.get(0).lon));


        // Centrar mapa en tu ubicación
         }

    // Clase para manejar familiares
    private static class Familiar {
        String nombre;
        double lat;
        double lon;

        Familiar(String nombre, double lat, double lon) {
            this.nombre = nombre;
            this.lat = lat;
            this.lon = lon;
        }
    }
}
