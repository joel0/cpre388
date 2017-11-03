package com.example.iceauror.lab10;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // TODO Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        double metersFromHome = SphericalUtil.computeDistanceBetween(
                new LatLng(45.613575,-122.563568),
                new LatLng(42.019719, -93.649773));
        double heading = SphericalUtil.computeHeading(
                new LatLng(42.019719, -93.649773),
                new LatLng(45.613575,-122.563568));
        LatLng nw = new LatLng(42.030350, -93.654296);
        LatLng ne = new LatLng(42.030001, -93.638706);
        LatLng sw = new LatLng(42.022844, -93.654260);
        LatLng se = new LatLng(42.022683, -93.639175);
        List<LatLng> iowa = new ArrayList<>();
        iowa.add(nw);
        iowa.add(ne);
        iowa.add(se);
        iowa.add(sw);
        iowa.add(nw);
        double area = SphericalUtil.computeArea(iowa);
        Toast.makeText(this, String.format(Locale.getDefault(),
                "Ames, IA is %d kilometers from Vancouver, WA.\nHeading: %d\n\nISU is about %.4f square kilometers.",
                (int) (metersFromHome / 1000),
                (int) heading,
                area / 1000 / 1000),
                Toast.LENGTH_LONG).show();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // TODO Add a marker in your hometown
        MarkerOptions hometown = new MarkerOptions();
        hometown.position(new LatLng(45.613575,-122.563568));
        hometown.title("Joel's Hometown");
        mMap.addMarker(hometown);
        // TODO Move the camera to the Marker Location
        CameraPosition pos = new CameraPosition(hometown.getPosition(), 9.5f, 0, 0);
        CameraUpdate hometownMove = CameraUpdateFactory.newCameraPosition(pos);
        mMap.animateCamera(hometownMove);
        // TODO Change the initial states of the Map.
        // See the layout XML
        // TODO Add the styling to the Map
        String nightJson = getMapStyle(R.raw.mapstyle_night);
        MapStyleOptions styleOptions = new MapStyleOptions(nightJson);
        mMap.setMapStyle(styleOptions);
    }

    private String getMapStyle(int resId) {
        // from https://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
        InputStream stream = getResources().openRawResource(R.raw.mapstyle_night);
        Scanner s = new Scanner(stream);
        s.useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
