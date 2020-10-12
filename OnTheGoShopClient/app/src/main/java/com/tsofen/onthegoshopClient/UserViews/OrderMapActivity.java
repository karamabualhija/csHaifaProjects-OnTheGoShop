package com.tsofen.onthegoshopClient.UserViews;
// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.PlaceOrderHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.PlaceOrderThread;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderMapActivity extends AppCompatActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {


    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final String API_KEY = "AIzaSyAKniSH0MaJOvFufHLPVO93q1IaNceypMU";

    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LatLng userLatLng;
    private Order newOrder;

    private HandlerThread placeOrderHandlerThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_map);
        newOrder = (Order) getIntent().getSerializableExtra("newOrder");
        placeOrderHandlerThread = new HandlerThread("placeOrderHandlerThread");
        placeOrderHandlerThread.start();

        getLocationPermission();

        //Initialize the SDK
        Places.initialize(this, API_KEY);

        //Create new places client instance
        PlacesClient placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                moveCamera(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude), DEFAULT_ZOOM);
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
                userLatLng = place.getLatLng();
                mMap.setOnMarkerClickListener(OrderMapActivity.this);
            }


            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng = marker.getPosition();
        Log.d(TAG, "onMarkerClick: latitude is : " + latLng.latitude + " longitude is: " + latLng.longitude);
        userLatLng = latLng;
        return false;
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            Log.d(TAG, "onComplete: GetDeviceLocation lat is: " + currentLocation.getLatitude() +
                                    "lon is: " + currentLocation.getLongitude());
                            userLatLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(OrderMapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(OrderMapActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    public void useCurrentLocation(View view) {
        getDeviceLocation();
        newOrder.setLatLng(userLatLng);
        ArrayList<Product> products = (ArrayList<Product>) newOrder.getProducts();
//        List<Product> proJson = new ArrayList<>();
//        try {
//            for (Product product : products) {
//                JSONObject object = new JSONObject();
//                object.put("id", product.getId());
//                object.put("name", product.getName());
//                object.put("amount", product.getAmount());
//                object.put("price", product.getPrice());
//                proJson.add(object);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        String jsonPro = products.toString();
        String userId = getSharedPreferences("login", MODE_PRIVATE).getString("id", null);
        PlaceOrderThread placeOrderThread = new PlaceOrderThread(jsonPro, Integer.parseInt(userId)
                , String.valueOf(newOrder.getLatLng().latitude), String.valueOf(newOrder.getLatLng().longitude),
                new PlaceOrderHandler() {
            @Override
            public void onOrderPlaced() {

            }

            @Override
            public void onFailure() {

            }
        });
        Handler handler = new Handler(placeOrderHandlerThread.getLooper());
        handler.post(placeOrderThread);
    }

    public void useChosenLocation(View view) {
        Log.d(TAG, "useChosenLocation: send the choosen location");
        newOrder.setLatLng(userLatLng);
        Log.d(TAG, "useChosenLocation: the new Order:" + newOrder.toString());
        JSONArray proJson = new JSONArray(newOrder.getProducts());
        String jsonPro = proJson.toString();
        String userId = getSharedPreferences("login", MODE_PRIVATE).getString("id", null);
        Log.d(TAG, "useChosenLocation: new Order Products: " + newOrder.getProducts().toString());
        PlaceOrderThread placeOrderThread = new PlaceOrderThread(jsonPro, Integer.parseInt(userId)
                , String.valueOf(newOrder.getLatLng().latitude), String.valueOf(newOrder.getLatLng().longitude),
                new PlaceOrderHandler() {
                    @Override
                    public void onOrderPlaced() {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
        Handler handler = new Handler(placeOrderHandlerThread.getLooper());
        handler.post(placeOrderThread);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (placeOrderHandlerThread!=null && placeOrderHandlerThread.isAlive())
            placeOrderHandlerThread.quit();
    }
}
