package com.titiit.ProjetCamp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import extra.LocationModule;

public class Vaccinsations extends AppCompatActivity {

    TextView text;
    LocationManager locationManager;
    LocationListener locationListener;
    TextView textDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccinsations);
        findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        text = findViewById(R.id.vaccText);
        textDate = findViewById(R.id.textDate);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        /*
        loc =  new LocationModule(Vaccinsations.this);
        if(loc.canGetLocation()){
            //this.text.setText(loc.getLongitude()+"," +loc.getLatitude());
            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + loc.getLatitude()+ "\nLong: " + loc.getLongitude(), Toast.LENGTH_LONG).show();
        }else{
            this.text.setText("Please activate GPS");
            loc.showSettingsAlert();
        }
        */

        Date date = Calendar.getInstance().getTime();
        String dateFormatted = date.getDate()+"/"+(date.getMonth()+1)+"/"+(date.getYear()+1900);
        textDate.setText(dateFormatted);

        locationListener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }

            @Override
            public void onLocationChanged(Location location) {
                // TODO Auto-generated method stub
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);

                text.setText(longitude+","+latitude);
                //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude+ "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                //double speed = location.getSpeed(); //spedd in meter/minute
                //speed = (speed * 3600) / 1000;      // speed in km/minute               Toast.makeText(GraphViews.this, "Current speed:" + location.getSpeed(),Toast.LENGTH_SHORT).show();
            }
        };
        configure_button();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
    }
}
