package com.navigine.mockgps;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity
{
    private static final String TAG_SELECT_ON_MAP = "SelectOnMap";
    private static final String TAG_CUSTOM_LOCATION = "CustomLocation";

    private Fragment mSelectOnMap    = new SelectOnMapFragment();
    private Fragment mCustomLocation = new CustomLocationFragment();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            mCustomLocation = new CustomLocationFragment();
            mSelectOnMap = new SelectOnMapFragment();
            manager.beginTransaction()
                    .add(R.id.main__frame_layout, mCustomLocation, TAG_CUSTOM_LOCATION)
                    .hide(mCustomLocation)
                    .add(R.id.main__frame_layout, mSelectOnMap, TAG_SELECT_ON_MAP)
                    .commitNow();
        } else {
            mCustomLocation = manager.findFragmentByTag(TAG_CUSTOM_LOCATION);
            mSelectOnMap = manager.findFragmentByTag(TAG_SELECT_ON_MAP);
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.select_on_map) {
                manager.beginTransaction().hide(mCustomLocation).show(mSelectOnMap).commit();
            } else if (id == R.id.custom_location) {
                manager.beginTransaction().hide(mSelectOnMap).show(mCustomLocation).commit();
            }
            return true;
        });
    }
}
