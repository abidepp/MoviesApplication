package com.example.userpc.myapplication.serviceClass;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by user pc on 2/9/2018.
 */
public class MyLocationService extends Service {

    LocationManager locationManager = null;
    private static final int LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 0;

    public class CurrentLocation {
        private Location currentLocation;

        public void setCurrentLocation(Location location) {
            this.currentLocation = location;
        }

        public String getCurrentLocation() {
            return currentLocation.toString();
        }
    }

    private class LocationListener implements android.location.LocationListener {
        Location mLastLocation;

        public LocationListener(String provider) {
            Log.i("locationService", "LocationListener " + provider);
            mLastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.i("locationService", "onLocationChanged: " + location);
            mLastLocation.set(location);
            CurrentLocation obj = new CurrentLocation();
            obj.setCurrentLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.i("locationService", "onProviderDisabled: " + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.i("locationService", "onProviderEnabled: " + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.i("locationService", "onStatusChanged: " + provider);
        }

    }

    LocationListener[] mLocationListeners = new LocationListener[]{
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("location service", "on start command called");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                CurrentLocation obj = new CurrentLocation();
//                if(obj.getCurrentLocation() != null)
//                {
//                    String location = obj.getCurrentLocation();
//                    Log.i("location service","inside start command---->"+location.toString());
//                }
//            }
//        }).start();

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                // ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                Toast.makeText(this, "please turn on location access from location settings", Toast.LENGTH_LONG).show();
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[1]);
            }
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, mLocationListeners[1]);
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("location service","on create called");
        initializeLocationManager();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("location service","on destroy called");
        if (mLocationListeners != null) {
            for (int i = 0; i < mLocationListeners.length; i++) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    Log.i("location service","permission is not granted. At service destroy phase");
                }
                locationManager.removeUpdates(mLocationListeners[i]);
            }
        }

    }


    public void initializeLocationManager()
    {
        if(locationManager == null)
        {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }


}
