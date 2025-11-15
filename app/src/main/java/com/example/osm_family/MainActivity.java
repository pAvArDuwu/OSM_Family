package com.example.osm_family;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

        Configuration.getInstance().load(
                this, getSharedPreferences("osmdroid_prefs", MODE_PRIVATE)
        );

        setContentView(R.layout.activity_main);

        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        ArrayList<Familiar> familiares = new ArrayList<>();
        familiares.add(new Familiar("Papa Pablo", -17.85449, -63.16202, R.drawable.papa));
        familiares.add(new Familiar("Mama Simona", -17.85450, -63.16202, R.drawable.mama));
        familiares.add(new Familiar("Hermano Jheison", -17.85461, -63.16202, R.drawable.hermano));
        familiares.add(new Familiar("Tio Marco", -17.8504125, -63.1553205, R.drawable.tio));
        familiares.add(new Familiar("Hermana Jhovana", -16.492211, -68.162935, R.drawable.hermana));

        // Agregar marcadores
        for (Familiar f : familiares) {
            Marker marker = new Marker(map);
            marker.setPosition(new GeoPoint(f.lat, f.lon));
            marker.setTitle(f.nombre);
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);

            // ICONO REDONDEADO + ESCALADO
            marker.setIcon(createRoundedIcon(f.imageResId, 140));

            map.getOverlays().add(marker);
        }

        map.getController().setZoom(18.0);
        map.getController().setCenter(new GeoPoint(familiares.get(0).lat, familiares.get(0).lon));
    }

    // -------------------------------
    // 🔵 MÉTODO PARA ICONOS REDONDEADOS
    // -------------------------------
    private Drawable createRoundedIcon(int resId, int sizePx) {
        Bitmap original = BitmapFactory.decodeResource(getResources(), resId);

        // Escalar imagen
        Bitmap scaled = Bitmap.createScaledBitmap(original, sizePx, sizePx, true);

        // Bitmap final redondeado
        Bitmap rounded = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(rounded);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        RectF rect = new RectF(0, 0, sizePx, sizePx);
        float radius = sizePx / 8f; // ← Borde suave (ajustable)

        // Dibujar bordes redondeados
        canvas.drawRoundRect(rect, radius, radius, paint);

        // Dibujar la imagen encima con modo SRC_IN (recorte)
        paint.setXfermode(new android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(scaled, 0, 0, paint);

        return new BitmapDrawable(getResources(), rounded);
    }

    // Clase familiar
    private static class Familiar {
        String nombre;
        double lat;
        double lon;
        int imageResId;

        Familiar(String nombre, double lat, double lon, int imageResId) {
            this.nombre = nombre;
            this.lat = lat;
            this.lon = lon;
            this.imageResId = imageResId;
        }
    }
}


