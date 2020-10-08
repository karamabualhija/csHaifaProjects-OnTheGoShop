package com.tsofen.onthegoshopClient.ManagerViews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tsofen.onthegoshopClient.Adapters.DriverAdapter;
import com.tsofen.onthegoshopClient.Beans.Driver;
import com.tsofen.onthegoshopClient.DataHandlers.AllDriverHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.AllDriversThread;

import java.util.ArrayList;

public class ManagerDrivers extends AppCompatActivity {

    private static final String TAG = "ManagerDrivers";

    private ListView mListView;
    private ArrayList<Driver> drivers;
    private DriverAdapter driverAdapter;
    private FloatingActionButton fab;

    private HandlerThread getDriversManagerHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_drivers);

        mListView = findViewById(R.id.managerDriverList);
        fab = findViewById(R.id.fabDriver);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerDrivers.this, NewDriver.class);
                startActivity(intent);
            }
        });

        getDriversManagerHandlerThread = new HandlerThread("ManagerDriverHandlerThread");
        getDriversManagerHandlerThread.start();

        AllDriversThread allDriversThread =new AllDriversThread(new AllDriverHandler() {
            @Override
            public void onDriversReceived(final ArrayList<Driver> driverArrayList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setDriversList(driverArrayList);
                    }
                });
            }
        });

        Handler handler = new Handler(getDriversManagerHandlerThread.getLooper());
        handler.post(allDriversThread);
    }

    private void setDriversList(ArrayList<Driver> drivers) {
        this.drivers = drivers;
        driverAdapter = new DriverAdapter(this, this.drivers);
        mListView.setAdapter(driverAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getDriversManagerHandlerThread!=null && getDriversManagerHandlerThread.isAlive())
            getDriversManagerHandlerThread.quit();
    }
}