package com.example.gpsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener, LocationListener {
    LocationManager locationManager;
    //Συλλογή πληροφορίας από αισθητήρες
    SensorManager sensorManager;
    Sensor sensor;
    TextView textView;
    Double mikos,platos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        textView = findViewById(R.id.textView);

    }
    public void gosensor (View view){
        sensorManager.registerListener(this,sensor,0);
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults){
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (grandResults[0]== PackageManager.PERMISSION_GRANTED)
            gpson(null);
        else Toast.makeText(this,"Please next time press Allow!...", Toast.LENGTH_LONG).show();
    }

    public void giveperm (View view) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},123);
        else Toast.makeText(this, "Permission already granded", Toast.LENGTH_LONG).show();
    }

    public void gpson (View view){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},123);
        else
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,0,0,this);
    }

    public void gotomaps (View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("mikos",mikos);
        intent.putExtra("platos",platos);
        startActivity(intent);
    }

    @Override
    public  void onSensorChanged (SensorEvent event) {
        float x_accel = event.values[0];
        textView.setText(String.valueOf(x_accel));
        if (x_accel>400)
            getWindow().getDecorView().setBackgroundColor(Color.RED);
        else getWindow().getDecorView().setBackgroundColor(Color.WHITE);
    }

    @Override
    public void onAccuracyChanged (Sensor sensor, int accuracy) {

    }

    @Override
    public void onLocationChanged(Location location){
        mikos = location.getLongitude();
        platos = location.getLatitude();
        textView.setText(mikos.toString()+","+platos.toString());
        location.getSpeed();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras){

    }

    @Override

    public void onProviderEnabled (String provider) {

    }

    @Override
    public void onProviderDisabled (String provider) {

    }
}
