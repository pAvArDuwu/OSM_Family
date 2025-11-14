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
        familiares.add(new Familiar("Yo", -16.4897, -68.1193));
        familiares.add(new Familiar("Papá", -16.4890, -68.1185));
        familiares.add(new Familiar("Mamá", -16.4902, -68.1190));
        familiares.add(new Familiar("Hermano", -16.4887, -68.1201));
        familiares.add(new Familiar("Hermana", -16.4895, -68.1178));

        // Agregar marcadores al mapa
        for (Familiar f : familiares) {
            Marker marker = new Marker(map);
            marker.setPosition(new GeoPoint(f.lat, f.lon));
            marker.setTitle(f.nombre);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(marker);
        }

        // Centrar mapa en tu ubicación
        map.getController().setZoom(16.0);
        map.getController().setCenter(new GeoPoint(familiares.get(0).lat, familiares.get(0).lon));
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
