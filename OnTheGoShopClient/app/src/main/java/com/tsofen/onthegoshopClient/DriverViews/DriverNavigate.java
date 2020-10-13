package com.tsofen.onthegoshopClient.DriverViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.tsofen.onthegoshopClient.Adapters.ProductOrderDetailAdapter;
import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.OrderDetailsHandler;
import com.tsofen.onthegoshopClient.DataHandlers.SetOrderDeliveredHandler;
import com.tsofen.onthegoshopClient.ManagerViews.MangerDriverDetails;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.OrderDetailsThread;
import com.tsofen.onthegoshopClient.ThreadServices.SetOrderDeliveredThread;
import com.tsofen.onthegoshopClient.UserViews.OrderMapActivity;

import java.util.ArrayList;
import java.util.List;

public class DriverNavigate extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "DriverNavigate";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private GoogleMap mMap;
    private Boolean mLocationPermissionsGranted = true;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private ListView orderProListV;
    private ArrayList<Product> productArrayList;
    private ProductOrderDetailAdapter listVAdapter;
    private HandlerThread orderProHandlerThread;
    private int orderId;
    private double lat;
    private double lon;
    private GeoApiContext mGeoApiContext = null;
    private LatLng deviceLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_navigate);

        orderProListV = findViewById(R.id.driverOrderProList);
        orderId = Integer.parseInt(getIntent().getStringExtra("orderId"));
        lat = Double.parseDouble(getIntent().getStringExtra("orderLat"));
        lon = Double.parseDouble(getIntent().getStringExtra("orderLon"));


        initMap();

        orderProHandlerThread = new HandlerThread("OrderProductsHandlerThread");
        orderProHandlerThread.start();
        getOrderProducts();


    }

    private void getOrderProducts() {
        OrderDetailsThread orderDetailsThread = new OrderDetailsThread(new OrderDetailsHandler() {
            @Override
            public void onProductsReceived(final ArrayList<Product> products) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        productArrayList = products;
                        listVAdapter = new ProductOrderDetailAdapter(DriverNavigate.this, productArrayList);
                        orderProListV.setAdapter(listVAdapter);
                    }
                });
            }
        }, orderId);
        Handler handler = new Handler(orderProHandlerThread.getLooper());
        handler.post(orderDetailsThread);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();
            mMap.addMarker(new MarkerOptions().title("dest").position(new LatLng(lat, lon)));
        }
    }

    private LatLng getDeviceLocation(){
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
                            deviceLoc = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            calculateDirections();
                            moveCamera(deviceLoc,15f);
                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(DriverNavigate.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
        return deviceLoc;
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.driverDestinationMap);

        mapFragment.getMapAsync(DriverNavigate.this);
        if (mGeoApiContext == null){
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.maps_api_key))
                    .build();
        }
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

    public void setOrderDelivered(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String driverId = sharedPreferences.getString("id", null);
        SetOrderDeliveredThread orderDeliveredThread = new SetOrderDeliveredThread(orderId, driverId,
                new SetOrderDeliveredHandler() {
                    @Override
                    public void onOrderChanged() {
                        Intent intent =new Intent(DriverNavigate.this, DriverMain.class);
                        finishAffinity();
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
        Handler handler = new Handler(orderProHandlerThread.getLooper());
        handler.post(orderDeliveredThread);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orderProHandlerThread!=null && orderProHandlerThread.isAlive())
            orderProHandlerThread.quit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =new Intent(DriverNavigate.this, DriverMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    private void calculateDirections(){
        Log.d(TAG, "calculateDirections: calculating directions.");


        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(
                lat,
                lon
        );
        DirectionsApiRequest directions = new DirectionsApiRequest(mGeoApiContext);

        directions.alternatives(false);
        directions.origin(
                new com.google.maps.model.LatLng(
                        deviceLoc.latitude,
                        deviceLoc.longitude
                )
        );
        Log.d(TAG, "calculateDirections: destination: " + destination.toString());
        directions.destination(destination).setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d(TAG, "onResult: routes: " + result.routes[0].toString());
                Log.d(TAG, "onResult: geocodedWayPoints: " + result.geocodedWaypoints[0].toString());
                addPolylinesToMap(result);
            }

            @Override
            public void onFailure(Throwable e) {
                Log.e(TAG, "onFailure: " + e.getMessage() );

            }
        });
    }
    private void addPolylinesToMap(final DirectionsResult result){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: result routes: " + result.routes.length);

                for(DirectionsRoute route: result.routes){
                    Log.d(TAG, "run: leg: " + route.legs[0].toString());
                    List<com.google.maps.model.LatLng> decodedPath = PolylineEncoding.decode(route.overviewPolyline.getEncodedPath());

                    List<LatLng> newDecodedPath = new ArrayList<>();

                    // This loops through all the LatLng coordinates of ONE polyline.
                    for(com.google.maps.model.LatLng latLng: decodedPath){

//                        Log.d(TAG, "run: latlng: " + latLng.toString());

                        newDecodedPath.add(new LatLng(
                                latLng.lat,
                                latLng.lng
                        ));
                    }
                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(newDecodedPath));
                    polyline.setColor(ContextCompat.getColor(DriverNavigate.this, R.color.middleLine));
                    polyline.setClickable(true);

                }
            }
        });
    }
}