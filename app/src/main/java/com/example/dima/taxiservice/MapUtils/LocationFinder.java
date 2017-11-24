package com.example.dima.taxiservice.MapUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Dima on 17.10.2017.
 */

public class LocationFinder implements LocationListener {
    public interface LocationFinderListener {
        void onSatusChange(String s);
        void onLocationFinded(Location location);
    }

    private LocationManager locationManager;
    private int DELAY = 10;
    private LocationFinderListener LocationFinderListener;
    private int reuestCode;

    public LocationFinder(LocationFinderListener LocationFinderListener, LocationManager locationManager,int request, int DELAY) {
        this.locationManager = locationManager;
        this.DELAY = DELAY;
        this.LocationFinderListener = LocationFinderListener;
        reuestCode=request;
    }
    
    public LocationFinder(LocationFinderListener LocationFinderListener, LocationManager locationManager,int request) {
        this.locationManager = locationManager;
        this.LocationFinderListener = LocationFinderListener;
        reuestCode=request;
    }

    public interface Ifinder{
        Context getContext();
        void requestPermissions(String... permission);
    }
    //    @SuppressLint("MissingPermission")
    public void startFinding(Ifinder context) {
        LocationFinderListener.onSatusChange("Start Finding");

        if (ActivityCompat.checkSelfPermission(context.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            context.requestPermissions(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    1000 * DELAY, 10, this);
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 1000 * DELAY, 10, this);
        }catch (Exception e){
            System.out.print(e.toString());
        }

        LocationFinderListener.onSatusChange(getStatus());

    }

    public void stopFinding() {
        locationManager.removeUpdates(this);
    }
    private String getStatus(){
        return "GPS: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER)
                +" NetWork: "
                + locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationFinderListener.onLocationFinded(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        LocationFinderListener.onSatusChange(s);
    }

    @Override
    public void onProviderEnabled(String s) {
        LocationFinderListener.onSatusChange(s);
    }

    @Override
    public void onProviderDisabled(String s) {
        LocationFinderListener.onSatusChange(s);
    }
}
