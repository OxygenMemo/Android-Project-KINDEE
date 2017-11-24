package com.example.apple.kindee.Controller;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.apple.kindee.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDetailActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Bundle bundle;


    int Res_id;
    String Res_name ;
    String Res_detail ;
    String Res_img_path ;
    Double Res_latitude;
    Double Res_longitude ;
    int Type_id ;
    String Type_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_detail);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bundle = getIntent().getExtras();

        Res_id = bundle.getInt("Res_id");
        Res_name = bundle.getString("Res_name");
         Res_detail = bundle.getString("Res_detail");
        Res_img_path = bundle.getString("Res_img_path");
        Res_latitude = bundle.getDouble("Res_latitude");
        Res_longitude = bundle.getDouble("Res_longitude");
        Type_id = bundle.getInt("Type_id");
        Type_name = bundle.getString("Type_name");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(Res_latitude, Res_longitude);
        mMap.moveCamera(CameraUpdateFactory//13.2812492,100.9264413
                .newLatLngZoom(sydney, 16));

        mMap.addMarker(new MarkerOptions().position(sydney).title(Res_name));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
