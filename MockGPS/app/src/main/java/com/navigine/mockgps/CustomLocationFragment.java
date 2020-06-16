package com.navigine.mockgps;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class CustomLocationFragment extends Fragment {

    @SuppressLint("StaticFieldLeak")
    static EditText longitude;
    @SuppressLint("StaticFieldLeak")
    static EditText latitude;

    static double longitudeText = 1.0;
    static double latitudeText = 1.0;

    public CustomLocationFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_location, container, false);

        longitude = view.findViewById(R.id.longitude);
        latitude  = view.findViewById(R.id.latitude);

        return view;
    }

    static void setLatLng(String mLat, String mLng) {
        latitudeText = Double.parseDouble(mLat);
        longitudeText = Double.parseDouble(mLng);

        latitude.setText(mLat);
        longitude.setText(mLng);
    }

    static String getLat() {
        return latitude.getText().toString();
    }

    static String getLng() {
        return longitude.getText().toString();
    }
}
