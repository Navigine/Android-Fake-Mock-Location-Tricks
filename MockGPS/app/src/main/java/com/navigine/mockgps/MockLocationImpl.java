package com.navigine.mockgps;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

public class MockLocationImpl {

    interface Listener {
        void onMockLocationError();
    }

    private static final long UPDATE_INTERVAL_MS = 1000L;
    private static final String[] PROVIDERS = {
            LocationManager.GPS_PROVIDER,
            LocationManager.NETWORK_PROVIDER
    };

    private LocationManager mLocationManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private final Listener mListener;
    private Runnable mRunnable;

    MockLocationImpl(Context context, Listener listener) {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        mListener = listener;
    }

    void startMockLocationUpdates(final double latitude, final double longitude) {
        try {
            for (String provider : PROVIDERS) registerProvider(provider);
        } catch (SecurityException e) {
            mListener.onMockLocationError();
            return;
        }

        mRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    for (String provider : PROVIDERS) pushLocation(provider, latitude, longitude);
                    mHandler.postDelayed(this, UPDATE_INTERVAL_MS);
                } catch (SecurityException | IllegalArgumentException e) {
                    stopMockLocationUpdates();
                    mListener.onMockLocationError();
                }
            }
        };
        mHandler.post(mRunnable);
    }

    void stopMockLocationUpdates() {
        if (mRunnable != null) {
            mHandler.removeCallbacks(mRunnable);
            mRunnable = null;
        }
        for (String provider : PROVIDERS) {
            try {
                mLocationManager.removeTestProvider(provider);
            } catch (SecurityException | IllegalArgumentException e) {
                // provider already removed or mock permission revoked: nothing left to clean up
            }
        }
    }

    private void registerProvider(String provider) {
        try {
            mLocationManager.removeTestProvider(provider);
        } catch (IllegalArgumentException e) {
            // no leftover test provider from a previous session
        }
        mLocationManager.addTestProvider(provider,
                false, false, false, false, false, true, true,
                Criteria.POWER_LOW, Criteria.ACCURACY_LOW);
        mLocationManager.setTestProviderEnabled(provider, true);
    }

    private void pushLocation(String provider, double latitude, double longitude) {
        Location location = new Location(provider);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAltitude(3F);
        location.setTime(System.currentTimeMillis());
        location.setSpeed(0.01F);
        location.setBearing(1F);
        location.setAccuracy(3F);
        location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            location.setBearingAccuracyDegrees(0.1F);
            location.setVerticalAccuracyMeters(0.1F);
            location.setSpeedAccuracyMetersPerSecond(0.01F);
        }
        mLocationManager.setTestProviderLocation(provider, location);
    }
}
