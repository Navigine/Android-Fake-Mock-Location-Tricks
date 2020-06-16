package com.navigine.mockgps;

import android.webkit.JavascriptInterface;

import java.util.Objects;

public class WebMap {
    private SelectOnMapFragment mFragment;

    WebMap(SelectOnMapFragment f) {
        mFragment = f;
    }

    @JavascriptInterface
    public void setPosition(final String str) {

        Objects.requireNonNull(mFragment.getActivity()).runOnUiThread(new Runnable() {

            @Override
            public void run() {
                String lat = str.substring(str.indexOf('(') + 1, str.indexOf(','));
                String lng = str.substring(str.indexOf(',') + 2, str.indexOf(')'));

                CustomLocationFragment.setLatLng(lat, lng);
            }
        });
    }

    @JavascriptInterface
    public double getLat() {

        String lat = CustomLocationFragment.getLat();

        if (lat.isEmpty()) {
            return (0);
        } else {
            return (Double.parseDouble(lat));
        }
    }

    @JavascriptInterface
    public double getLng() {

        String lng = CustomLocationFragment.getLng();

        if (lng.isEmpty()) {
            return (0);
        } else {
            return (Double.parseDouble(lng));
        }

    }
}
