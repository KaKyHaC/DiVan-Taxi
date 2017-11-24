package com.example.dima.taxiservice.MapUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Dima on 17.10.2017.
 */

public class LocationFinder {
    public interface StatusListener {
        void onSatusChange(String s);
    }

    private LocationManager locationManager;
    private int DELAY = 10;
    private LocationListener locationListener;
    private StatusListener statusListener;

    public LocationFinder(StatusListener statusListener, LocationManager locationManager, LocationListener locationListener, int DELAY) {
        this.locationManager = locationManager;
        this.DELAY = DELAY;
        this.locationListener = locationListener;
        this.statusListener = statusListener;
    }

    public LocationFinder(StatusListener statusListener, LocationManager locationManager, LocationListener locationListener) {
        this.locationManager = locationManager;
        this.locationListener = locationListener;
        this.statusListener = statusListener;
    }

    public interface Ifinder{
        Context getContext();
        void requestPermissions(String... permission);
    }
    //    @SuppressLint("MissingPermission")
    public void startFinding(Ifinder context) {
        statusListener.onSatusChange("Start Finding");

        if (ActivityCompat.checkSelfPermission(context.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            context.requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * DELAY, 10, locationListener);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * DELAY, 10,
                    locationListener);
        }catch (Exception e){
            System.out.print(e.toString());
        }

        statusListener.onSatusChange(getStatus());

    }

    public void stopFinding() {
        locationManager.removeUpdates(locationListener);
    }
    private String getStatus(){
        return "GPS: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)
                +" NetWork: "
                + locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
