package com.example.sendcoordinatesapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
public class MainActivity extends AppCompatActivity {
    private TextView textViewLatitude;
    private TextView textViewLongitude;
    private Location mobileLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setIdElements();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.SEND_SMS}, 1);
        } else
        {
            // Write you code here if permission already given.
            setLocation();
        }
    }
    private void setLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        final LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(@NonNull Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                textViewLatitude.setText(""+latitude);
                textViewLongitude.setText(""+longitude);
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 1, locationListener);
        lm.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 1, 1, locationListener);
        mobileLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        textViewLatitude.setText(""+mobileLocation.getLatitude());
        textViewLongitude.setText(""+mobileLocation.getLongitude());
    }
    private void setIdElements()
    {
        textViewLatitude = (TextView) findViewById(R.id.textViewLatitude);
        textViewLongitude = (TextView) findViewById(R.id.textViewLongitude);
    }
    public void imageButtonEmailOnClick(View view)
    {
        Intent intent = new Intent(this, SendEmailActivity.class);
        startActivity(intent);
    }

    public void imageButtonSMSOnClick(View view)
    {
        Intent intent = new Intent(this, SendSMSActivity.class);
        startActivity(intent);
    }
}