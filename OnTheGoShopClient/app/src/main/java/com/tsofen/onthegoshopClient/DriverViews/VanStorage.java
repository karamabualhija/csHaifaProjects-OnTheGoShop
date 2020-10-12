package com.tsofen.onthegoshopClient.DriverViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tsofen.onthegoshopClient.Adapters.Storage_productAdapter;
import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.DriverProductsHandler;
import com.tsofen.onthegoshopClient.LogIn.MainActivity;
import com.tsofen.onthegoshopClient.ManagerViews.ManagerMain;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.DriverProductsThread;

import java.util.ArrayList;

public class VanStorage extends AppCompatActivity {

    private static final String TAG = "VanStorage";

    private FloatingActionButton fab;
    private ListView productsListV;
    private ArrayList<Product> products;
    private Storage_productAdapter productAdapter;

    private HandlerThread allVanProductHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_van_storage);

        productsListV = findViewById(R.id.vanStorageList);
        fab = findViewById(R.id.fabVanStorage);

        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
        String vanNum = sharedPreferences.getString("vanNum", null);

        allVanProductHandlerThread = new HandlerThread("allProductHandlerThread");
        allVanProductHandlerThread.start();

        if (vanNum!=null){
            DriverProductsThread driverProductsThread  = new DriverProductsThread(vanNum, new DriverProductsHandler() {
                @Override
                public void onProductsReceived(final ArrayList<Product> productArrayList) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            products = productArrayList;
                            productAdapter = new Storage_productAdapter(VanStorage.this, products);
                            productsListV.setAdapter(productAdapter);
                        }
                    });
                }

                @Override
                public void onFailure() {
                    Log.d(TAG, "onFailure: failed to download the products");
                }
            });
            Handler handler = new Handler(allVanProductHandlerThread.getLooper());
            handler.post(driverProductsThread);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VanStorage.this, DriverNewProduct.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, DriverMain.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (allVanProductHandlerThread !=null && allVanProductHandlerThread.isAlive())
            allVanProductHandlerThread.quit();
    }
}