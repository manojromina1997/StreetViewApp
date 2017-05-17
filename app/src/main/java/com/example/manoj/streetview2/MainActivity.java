package com.example.manoj.streetview2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public class MainActivity extends AppCompatActivity implements OnStreetViewPanoramaReadyCallback{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager()
                .findFragmentById(R.id.streetviewpanoarma);

        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);
    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama panorma) {


        //preference manager will send the data here
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String geo = sharedPrefs.getString(
                getString(R.string.place_name_key),
                getString(R.string.place_name_default));


        String[] separatedString = geo.split(",");

        String latitude = separatedString[0];
        String longitude = separatedString[1];



        System.out.println("Geo Location is " +geo);
        System.out.println("Latititude is " +latitude);
        System.out.println("Longitude is " +longitude);


        double lat= Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);

        //new york
        panorma.setPosition(new LatLng(lat,lon));

        //panorma.setPosition(new LatLng(18.9219841,72.8324656));
        //
        // panorma.setStreetNamesEnabled(true);

        StreetViewPanoramaCamera camera = new StreetViewPanoramaCamera.Builder()
                .bearing(180)
                .build();
        panorma.animateTo(camera,10000);


    }
    //this is for above setting menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //if the setting is selected then the setting activity will be called

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
