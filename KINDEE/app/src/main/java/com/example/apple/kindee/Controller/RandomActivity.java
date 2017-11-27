package com.example.apple.kindee.Controller;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apple.kindee.Model.Resturant;
import com.example.apple.kindee.Model.ResultLogin;
import com.example.apple.kindee.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Apple on 11/26/2017 AD.
 */

public class RandomActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private boolean GPS_OPEN =false;
    private Location mylocation=null;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = RandomActivity.class.getSimpleName();

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


    }
    private void checkGPS(){
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(GPS_OPEN=!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Ask the user to enable GPS
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Manager");
            builder.setMessage("Would you like to enable GPS?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //Launch settings, allowing user to make a change
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //No location service, no Activity
                    finish();
                }
            });
            builder.create().show();
        }else{
            GPS_OPEN=true;
        }
    }
    private void getRandomResApi(String url){
        //final TextView tv= (TextView) findViewById(R.id.tv_Register_register);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setPrettyPrinting();

                        Gson gson = gsonBuilder.create();
                        Resturant[] result = gson.fromJson(response.toString(),Resturant[].class);
                        if(result.length > 0) {
                            Intent i = new Intent(getBaseContext(), DetailActivity.class);

                            i.putExtra("Res_id", result[0].Res_id);
                            i.putExtra("Res_name", result[0].Res_name);
                            i.putExtra("Res_detail", result[0].Res_detail);
                            i.putExtra("Res_img_path", result[0].Res_img_path);
                            i.putExtra("Res_latitude", result[0].Res_latitude);
                            i.putExtra("Res_longitude", result[0].Res_longitude);
                            i.putExtra("Type_id", result[0].Type_id);
                            i.putExtra("Type_name", result[0].Type_name);

                            startActivity(i);
                        }else{
                            Toast.makeText(getBaseContext(),"ไม่มีร้านไกล้เคียง",Toast.LENGTH_SHORT).show();
                        }
                        //ArrayAdapter<String> arrayAdapter = ArrayAdapter
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //tv.setText(error.toString());
            }
        });
        queue.add(stringRequest);
    }

    public void callDetailActivity(View view){
        checkGPS();
        if(GPS_OPEN&&mylocation!=null) {
            //TextView tv = (TextView) findViewById(R.id.tv_gupu_random);
            //tv.setText(mylocation.getLatitude() + " " + mylocation.getLongitude());
            Spinner spinner = findViewById(R.id.spinner_lenght_random);
            String url = "http://angsila.cs.buu.ac.th/~58160698/KINDEE_API/KINDEE/index.php/Resturant_controller/randomRes/"+mylocation.getLatitude()
                    +"/"+mylocation.getLongitude()+"/"+spinner.getSelectedItem().toString();

            getRandomResApi(url);

        }
        else{
            if(GPS_OPEN) {
                Toast.makeText(this, "gps off", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "plese wait gps acive", Toast.LENGTH_SHORT).show();
            }

        }
        //Toast.makeText(this,mylocation.getLatitude()+"",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        mylocation = location;
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }


}
