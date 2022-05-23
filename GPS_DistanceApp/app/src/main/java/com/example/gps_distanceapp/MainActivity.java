package com.example.gps_distanceapp;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView latitudeTv, longitudeTv, addressTv, distanceTv, name;
    EditText eT1;
    Button send;
    Location loc;
    int c = 0;
    TabItem curLoc, gpsTab;
    Boolean gpsT = false;
    TabLayout tabLayout;
    ViewPager viewPager;
    int count = 0;
    TabLayout.Tab tab1;
    LocationManager locationManager;
    ArrayList<Address> addresses;
    double distance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudeTv = findViewById(R.id.lat);
        longitudeTv = findViewById(R.id.lon);
        addressTv = findViewById(R.id.add);
        distanceTv = findViewById(R.id.dis);
        gpsTab = findViewById(R.id.gpsTab);
        curLoc = findViewById(R.id.currentLocation);
        tabLayout = findViewById(R.id.id_tablayout);
        name = findViewById(R.id.name);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener localListener = new gpsListener();

        //onRequestPermissionsResult();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, localListener);
//        SmsManager smsManager = SmsManager.getDefault();
//        Log.d("sms", String.valueOf(smsManager));
//        smsManager.sendTextMessage(eT1.getText().toString(), null, ("Addresss" + addressTv.getText().toString()), null, null);


   tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            TabLayout tabLayout = (TabLayout) findViewById(R.id.id_tablayout); // get the reference of TabLayout
            int selectedTabPosition = tabLayout.getSelectedTabPosition();
            GetTabAt(selectedTabPosition);
            if(selectedTabPosition==1) {
                openActivity2();
            }
            if(selectedTabPosition==0)
                Toast.makeText(MainActivity.this, "Take care of the problem", Toast.LENGTH_SHORT).show();
       }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {


        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding

            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
            return;
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }
    }

    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //locationManager.removeUpdates();
        }

    }


    void GetTabAt(int index) {

        count++;
        TabLayout tabLayout = (TabLayout) findViewById(R.id.id_tablayout); // get the reference of TabLayout
        TabLayout.Tab tab = tabLayout.getTabAt(index);
        if(count>1) {
            TabLayout.Tab temp = tab;
            tab = tab1;
            tab1 = temp;
        }

    }

    public void openActivity2()
    {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }


    class gpsListener implements LocationListener
    {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            c++;

            double latitude;
            double longitude; 
            latitude = location.getLatitude();
            Log.d("lat", String.valueOf(latitude));
            longitude = location.getLongitude();
            Log.d("lon", String.valueOf(longitude));
            latitudeTv.setText("Latitude: "+latitude);
            longitudeTv.setText("Longitude: "+longitude);

            String myAddress = "";
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String address = addresses.get(0).getAddressLine(0);
            myAddress = addresses.get(0).getLocality();
            Log.d("TAG", myAddress);
            Log.d("TAG", address);
            try {
                addressTv.setText("Address: "+address);
                if(c > 1) {
                    float loc1 = location.distanceTo(loc);
                    Log.d("loc1", String.valueOf(loc1));
                    distance += (double)loc1;
                    distanceTv.setText("Distance: " + distance+" meters");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            if(c <= 1 ) {
                loc = location;
            }
        }


        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(@NonNull String provider) {

        }

        @Override
        public void onProviderDisabled(@NonNull String provider) {

        }
    }
}