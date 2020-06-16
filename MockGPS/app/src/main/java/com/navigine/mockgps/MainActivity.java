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
    private Fragment mSelectOnMap    = new SelectOnMapFragment();
    private Fragment mCustomLocation = new CustomLocationFragment();
    private FragmentManager mManager = getSupportFragmentManager();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager.beginTransaction().add(R.id.main__frame_layout, mCustomLocation, "CustomLocation").hide(mCustomLocation).commitAllowingStateLoss();
        mManager.beginTransaction().add(R.id.main__frame_layout, mSelectOnMap, "SelectOnMap").commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.select_on_map:
                        mManager.beginTransaction().hide(mCustomLocation).show(mSelectOnMap).commitAllowingStateLoss();
                        break;
                    case R.id.custom_location:
                        mManager.beginTransaction().hide(mSelectOnMap).show(mCustomLocation).commitAllowingStateLoss();
                        break;
                }
                return true;
            }
        });
    }
}
