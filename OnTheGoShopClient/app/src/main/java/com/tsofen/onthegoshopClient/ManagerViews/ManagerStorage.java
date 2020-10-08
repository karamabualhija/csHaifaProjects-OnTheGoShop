package com.tsofen.onthegoshopClient.ManagerViews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tsofen.onthegoshopClient.Adapters.Storage_productAdapter;
import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.AllProductsHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.AllProductsThread;

import java.util.ArrayList;

public class ManagerStorage extends AppCompatActivity {

    private static final String TAG = "ManagerStorage";

    private ListView productListView;
    private FloatingActionButton floatingActionButton;
    private ArrayList<Product> products;
    private Storage_productAdapter storageProductAdapter;

    private HandlerThread managerStorageHandlerThread;
    private Handler managerStorageHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_storage);

        productListView = findViewById(R.id.managerStorageList);
        floatingActionButton =findViewById(R.id.fabStorage);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerStorage.this, NewProduct.class);
                startActivity(intent);
            }
        });

        managerStorageHandlerThread = new HandlerThread("managerStorageHandlerThread");
        managerStorageHandlerThread.start();
        managerStorageHandler = new Handler(managerStorageHandlerThread.getLooper());


        products = new ArrayList<>();

        AllProductsThread allProductsThread = new AllProductsThread(new AllProductsHandler() {
            @Override
            public void onDataReceived(final ArrayList<Product> productsReceived) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setListWithProducts(productsReceived);
                    }
                });
            }
        }, "manager");

        managerStorageHandler.post(allProductsThread);
    }

    private void setListWithProducts(ArrayList<Product> productsReceived) {
        products = productsReceived;
        storageProductAdapter = new Storage_productAdapter(this, products);
        productListView.setAdapter(storageProductAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (managerStorageHandlerThread!=null && managerStorageHandlerThread.isAlive())
            managerStorageHandlerThread.quit();
    }
}