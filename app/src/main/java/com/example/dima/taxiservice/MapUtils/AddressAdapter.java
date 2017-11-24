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



public class AddressAdapter {
    @FunctionalInterface
    public interface AddressListener {
        void onAddressFinded(LatLng location, Address address, int request);
    }
    Geocoder geocoder;
    AddressListener addressListener;

    public AddressAdapter( Geocoder geocoder, AddressListener addressListener) {
        this.geocoder = geocoder;
        this.addressListener = addressListener;
    }

    public void findAddress(LatLng location,int request,AddressListener listener){
        try {
//            statusListener.onSatusChange("start finding address");
            List<Address> addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1);

            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                listener.onAddressFinded(location,returnedAddress, request);
//                statusListener.onSatusChange("address finded");
            } else {
                listener.onAddressFinded(location,null, request);
//                statusListener.onSatusChange("address finding failed");
            }
        } catch (IOException e) {
            listener.onAddressFinded(location,null, request);
//            statusListener.onSatusChange("address finding failed");
        }
    }
    public void findAddress(LatLng location,int request){
        findAddress(location,request,addressListener);
    }
}

