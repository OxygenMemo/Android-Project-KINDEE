package com.example.apple.kindee.Controller;

import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apple.kindee.R;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsUploadActivity extends FragmentActivity implements
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
        OnMapReadyCallback {
    //private TextView tv = (TextView) findViewById(R.id.tv_map);
    private GoogleMap mMap;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private  double x;
    private  double y;
    private Marker marker;
    // PlaceDetectionClient

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_upload);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



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
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);

        // Show Sydney on the map.
        mMap.moveCamera(CameraUpdateFactory//13.2812492,100.9264413
                .newLatLngZoom(new LatLng(13.2812492, 100.9264413), 14));
        //mMap.addMarker(new MarkerOptions().position(new LatLng(-33.87365, 151.20689)).title("g"));
        if(x != 0 || y != 0 ){
            mMap.addMarker(new MarkerOptions().position(new LatLng(x,y)));
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.clear();
                LatLng click = new LatLng(latLng.latitude,latLng.longitude);
                MarkerOptions mk = new MarkerOptions().position(click).title("myclick");
                //Toast.makeText(getBaseContext(),latLng.latitude+" "+latLng.longitude,Toast.LENGTH_SHORT).show();
                x = latLng.latitude;
                y = latLng.longitude;
                marker = mMap.addMarker(mk);
            }
        });
    }

    @Override
    public void onCameraMoveStarted(int reason) {

        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            //Toast.makeText(this, "The user gestured on the map.",Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            //Toast.makeText(this, "The user tapped something on the map.", Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            //Toast.makeText(this, "The app moved the camera.", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                //mMap.clear();
                LatLng click = new LatLng(event.getX(),event.getY());
                MarkerOptions mk = new MarkerOptions().position(click).title("myclick");
                Toast.makeText(this,event.getX()+" "+event.getY(),Toast.LENGTH_SHORT).show();
                marker = mMap.addMarker(mk);

                marker.setTag(0);

                break;

            case MotionEvent.ACTION_UP:

                LatLng click2 = new LatLng(event.getX(),event.getY());
                marker = mMap.addMarker(new MarkerOptions().position(click2).title("myclick"));
                Toast.makeText(this,event.getX()+" "+event.getY(),Toast.LENGTH_SHORT).show();
                marker.setTag(0);

                break;

        }
        return super.dispatchTouchEvent(event);
    }
    */
    @Override
    public void onCameraMove() {
        //Toast.makeText(this, "The camera is moving.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMoveCanceled() {
        //Toast.makeText(this, "Camera movement canceled.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraIdle() {
        //Toast.makeText(this, "The camera has stopped moving.", Toast.LENGTH_SHORT).show();
    }

    public void send_location(View view){
        Intent i = new Intent(this,AddresturantActivity.class);
        i.putExtra("latitude",x);
        i.putExtra("longitude",y);
        setResult(2,i);
        finish();
    }



}
