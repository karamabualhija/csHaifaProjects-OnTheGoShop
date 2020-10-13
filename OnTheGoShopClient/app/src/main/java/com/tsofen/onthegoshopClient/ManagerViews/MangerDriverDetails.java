package com.tsofen.onthegoshopClient.ManagerViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
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
import com.tsofen.onthegoshopClient.DriverViews.DriverMain;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.DriverLocationThread;
import com.tsofen.onthegoshopClient.ThreadServices.DriverOrdersThread;
import com.tsofen.onthegoshopClient.ThreadServices.DriverProductsThread;
import com.tsofen.onthegoshopClient.UserViews.OrderMapActivity;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MangerDriverDetails extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MangerDriverDetails";

    private static final long sleepTime = 60 * 1000;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private String driverId;

    private ListView driverProductsListV;
    private ArrayList<Product> products;
    private DriverDetailsProductAdapter productAdapter;

    private ListView driverOrdersList;
    private ArrayList<Order> orders;
    private DriverDetailsOrderAdapter orderAdapter;

    private Timer timer;
    private HandlerThread driverDetailsProductHandlerThread;
    private HandlerThread driverDetailsOrderHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manger_driver_details);

        driverId = getIntent().getStringExtra("driverId");
        driverProductsListV = findViewById(R.id.driverDetailsList);
        driverOrdersList = findViewById(R.id.driverDetailsOrderList);

        initMap();

        refreshDriverLocation();

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


    public void refreshDriverLocation(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                getDriverLocation();  // display the data
            }
        }, 1000, sleepTime);
    }

    private void getDriverLocation() {
        HandlerThread driverLocationHandlerThread = new HandlerThread("driverLocationHandlerThread");
        driverLocationHandlerThread.start();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (driverDetailsOrderHandlerThread!=null && driverDetailsOrderHandlerThread.isAlive())
            driverDetailsOrderHandlerThread.quit();
        if (driverDetailsProductHandlerThread!=null && driverDetailsProductHandlerThread.isAlive())
            driverDetailsProductHandlerThread.quit();
        timer.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
        Intent intent = new Intent(this, ManagerMain.class);
        startActivity(intent);
    }
}