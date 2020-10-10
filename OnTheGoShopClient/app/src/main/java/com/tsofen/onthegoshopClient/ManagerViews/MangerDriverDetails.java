package com.tsofen.onthegoshopClient.ManagerViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tsofen.onthegoshopClient.Adapters.DriverDetailsOrderAdapter;
import com.tsofen.onthegoshopClient.Adapters.DriverDetailsProductAdapter;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.DriverLocationHandler;
import com.tsofen.onthegoshopClient.DataHandlers.DriverOrdersHandler;
import com.tsofen.onthegoshopClient.DataHandlers.DriverProductsHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.DriverLocationThread;
import com.tsofen.onthegoshopClient.ThreadServices.DriverOrdersThread;
import com.tsofen.onthegoshopClient.ThreadServices.DriverProductsThread;
import com.tsofen.onthegoshopClient.UserViews.OrderMapActivity;

import java.util.ArrayList;

public class MangerDriverDetails extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MangerDriverDetails";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private long sleepTime = 60 * 1000;
    private boolean refreshDriverLocation = true;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private String driverId;

    private ListView driverProductsListV;
    private ArrayList<Product> products;
    private DriverDetailsProductAdapter productAdapter;

    private ListView driverOrdersList;
    private ArrayList<Order> orders;
    private DriverDetailsOrderAdapter orderAdapter;

    private HandlerThread driverDetailsProductHandlerThread;
    private HandlerThread driverDetailsOrderHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_driver_details);

        driverId = getIntent().getStringExtra("driverId");
        driverProductsListV = findViewById(R.id.driverDetailsList);
        driverOrdersList = findViewById(R.id.driverDetailsOrderList);

        getLocationPermission();

        getDriverProducts();

        getDriverOrders();
        
    }

    private void getDriverOrders() {
        driverDetailsOrderHandlerThread = new HandlerThread("driverDetailsOrderHandlerThread");
        driverDetailsOrderHandlerThread.start();
        DriverOrdersThread driverOrdersThread = new DriverOrdersThread(driverId, new DriverOrdersHandler() {
            @Override
            public void onOrdersReceived(final ArrayList<Order> orderArrayList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        orders = orderArrayList;
                        orderAdapter = new DriverDetailsOrderAdapter(MangerDriverDetails.this,orders);
                        driverOrdersList.setAdapter(orderAdapter);
                    }
                });
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure: failed to receive the orders");
                Toast.makeText(MangerDriverDetails.this, "Failed to get the orders", Toast.LENGTH_SHORT).show();
            }
        });
        Handler handler = new Handler(driverDetailsOrderHandlerThread.getLooper());
        handler.post(driverOrdersThread);
    }

    private void getDriverProducts() {
        driverDetailsProductHandlerThread = new HandlerThread("driverDetailsProductHandlerThread");
        driverDetailsProductHandlerThread.start();
        DriverProductsThread driverProductsThread = new DriverProductsThread(driverId, new DriverProductsHandler() {
            @Override
            public void onProductsReceived(final ArrayList<Product> productArrayList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        products = productArrayList;
                        productAdapter = new DriverDetailsProductAdapter(MangerDriverDetails.this, products);
                        driverProductsListV.setAdapter(productAdapter);
                    }
                });
            }

            @Override
            public void onFailure() {
                Log.d(TAG, "onFailure: failed to receive the products");
                Toast.makeText(MangerDriverDetails.this, "Failed to get the products", Toast.LENGTH_SHORT).show();
            }
        });
        Handler handler = new Handler(driverDetailsProductHandlerThread.getLooper());
        handler.post(driverProductsThread);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDriverLocation();
        }
    }

    private void getDriverLocation() {
        HandlerThread driverLocationHandlerThread = new HandlerThread("driverLocationHandlerThread");
        driverLocationHandlerThread.start();
        while (refreshDriverLocation){
            DriverLocationThread driverLocationThread = new DriverLocationThread(driverId, new DriverLocationHandler() {
                @Override
                public void onLocationReceived(final LatLng latLng) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setMarkerOnMap(latLng);
                        }
                    });
                }

                @Override
                public void onFailure() {

                }
            });
            Handler handler = new Handler(driverLocationHandlerThread.getLooper());
            handler.post(driverLocationThread);
            SystemClock.sleep(sleepTime);
        }
    }

    private void setMarkerOnMap(LatLng latLng){
        mMap.clear();
        mMap.addMarker(new MarkerOptions().title("Driver Location").position(latLng));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 0));
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.driverDetailsMap);

        mapFragment.getMapAsync(MangerDriverDetails.this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refreshDriverLocation = false;
        if (driverDetailsOrderHandlerThread!=null && driverDetailsOrderHandlerThread.isAlive())
            driverDetailsOrderHandlerThread.quit();
        if (driverDetailsProductHandlerThread!=null && driverDetailsProductHandlerThread.isAlive())
            driverDetailsProductHandlerThread.quit();
    }
}