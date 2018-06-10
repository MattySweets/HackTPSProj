package com.theboys.hacktps;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private double lon = Math.random(), lat = Math.random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Write a message to the database
        //myRef.setValue("hello");
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityCompat.requestPermissions(
                MainActivity.this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference longitude = database.getReference("longitude");
                    DatabaseReference latitude = database.getReference("latitude");

                    LocationManager locMan = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    Location lastLoc = null;
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    lastLoc = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (lastLoc != null) {
                        lat = lastLoc.getLatitude();
                        lon = lastLoc.getLongitude();
                    }

                    longitude.setValue(lon);
                    latitude.setValue(lat);

                } else {

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}