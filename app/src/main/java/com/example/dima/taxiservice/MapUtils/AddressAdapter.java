package com.example.dima.taxiservice.MapUtils;


import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;

/**
 * Created by Dima on 17.10.2017.
 */



public class AddressAdapter implements LocationListener {
    public interface AddressListener {
        enum Result{Success,Failed,}
        void onAddressChange(LatLng location, Address address, Result result);
    }
    Geocoder geocoder;
    AddressListener addressListener;
    LocationFinder.StatusListener statusListener;

    public AddressAdapter(LocationFinder.StatusListener statusListener, Geocoder geocoder, AddressListener addressListener) {
        this.geocoder = geocoder;
        this.addressListener = addressListener;
        this.statusListener=statusListener;
    }

    @Override
    public void onLocationChanged(Location location) {
        statusListener.onSatusChange("Location finded: "+location);
        findAddress(new LatLng(location.getLatitude(),location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void findAddress(LatLng location){
        try {
            statusListener.onSatusChange("start finding address");
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                addressListener.onAddressChange(location,returnedAddress, AddressListener.Result.Success);
                statusListener.onSatusChange("address finded");
            } else {
                addressListener.onAddressChange(location,null, AddressListener.Result.Failed);
                statusListener.onSatusChange("address finding failed");
            }
        } catch (IOException e) {
            addressListener.onAddressChange(location,null, AddressListener.Result.Failed);
            statusListener.onSatusChange("address finding failed");
        }
    }
}

