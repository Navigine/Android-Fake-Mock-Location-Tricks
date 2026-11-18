package com.navigine.mockgps;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import java.util.Objects;

public class WebMap {
    private SelectOnMapFragment mFragment;

    WebMap(SelectOnMapFragment f) {
        mFragment = f;
    }

    @JavascriptInterface
    public void setPosition(final String str) {
        Activity activity = mFragment.getActivity();
        if (activity == null) return;

        activity.runOnUiThread(() -> {
            String lat = str.substring(str.indexOf('(') + 1, str.indexOf(','));
            String lng = str.substring(str.indexOf(',') + 1, str.indexOf(')'));
            CustomLocationFragment.setLatLng(lat, lng);
        });
    }

    @JavascriptInterface
    public double getLat() {
        Double lat = CustomLocationFragment.parseCoordinate(CustomLocationFragment.getLat());
        return lat != null ? lat : 0;
    }

    @JavascriptInterface
    public double getLng() {
        Double lng = CustomLocationFragment.parseCoordinate(CustomLocationFragment.getLng());
        return lng != null ? lng : 0;
    }
}
