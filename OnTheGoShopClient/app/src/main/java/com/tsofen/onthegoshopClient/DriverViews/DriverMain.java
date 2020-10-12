package com.tsofen.onthegoshopClient.DriverViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tsofen.onthegoshopClient.Adapters.DriverOrderNavAdapter;
import com.tsofen.onthegoshopClient.BackgroundServices.LocationService;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.DataHandlers.DriverOrdersHandler;
import com.tsofen.onthegoshopClient.LogIn.MainActivity;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.DriverOrdersThread;
import com.tsofen.onthegoshopClient.ThreadServices.LogoutThread;

import java.util.ArrayList;

public class DriverMain extends AppCompatActivity {

    private static final String TAG = "DriverMain";

    private ListView driverOrdersList;
    private ArrayList<Order> orders;
    private DriverOrderNavAdapter navAdapter;

    private HandlerThread driverOrderHandlerThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);

        driverOrdersList = findViewById(R.id.driversOrderNavList);

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String vanNum = sharedPreferences.getString("vanNum", null);

        driverOrderHandlerThread = new HandlerThread("driverOrderHandlerThread");
        driverOrderHandlerThread.start();

        if (vanNum!=null){
            DriverOrdersThread driverOrdersThread = new DriverOrdersThread(vanNum, new DriverOrdersHandler() {
                @Override
                public void onOrdersReceived(final ArrayList<Order> orderArrayList) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            orders = orderArrayList;
                            navAdapter = new DriverOrderNavAdapter(DriverMain.this, orders);
                            driverOrdersList.setAdapter(navAdapter);
                        }
                    });
                }

                @Override
                public void onFailure() {
                    Log.d(TAG, "onFailure: there was a Failure getting the orders");
                }
            });
            Handler handler = new Handler(driverOrderHandlerThread.getLooper());
            handler.post(driverOrdersThread);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (driverOrderHandlerThread!=null && driverOrderHandlerThread.isAlive())
            driverOrderHandlerThread.quit();
    }


    public void driverLogout(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor edit  = sharedPreferences.edit();
        edit.remove("loggedIn");
        edit.remove("vanNum");
        edit.apply();

        Intent serviceIntent = new Intent(this, LocationService.class);
        stopService(serviceIntent);
        HandlerThread logoutHandlerThread = new HandlerThread("logoutHandlerThread");
        logoutHandlerThread.start();
        Handler handler =  new Handler(logoutHandlerThread.getLooper());
        handler.post(new LogoutThread());

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void showVanProducts(View view) {
        Intent intent = new Intent(this, VanStorage.class);
        startActivity(intent);
    }

    public void navigateToOrder(View view){
        LinearLayout linearLayout = (LinearLayout) view.getParent();
        TextView orderId = linearLayout.findViewById(R.id.orderIdDriverNav);
        TextView orderLat = linearLayout.findViewById(R.id.orderLatDriverNav);
        TextView orderLon = linearLayout.findViewById(R.id.orderLonDriverNav);
        String id = orderId.getText().toString();
        String lat = orderLat.getText().toString();
        String lon = orderLon.getText().toString();
        Intent intent = new Intent(this, DriverNavigate.class);
        intent.putExtra("orderId", id);
        intent.putExtra("orderLat", lat);
        intent.putExtra("orderLon", lon);
        startActivity(intent);
    }
}