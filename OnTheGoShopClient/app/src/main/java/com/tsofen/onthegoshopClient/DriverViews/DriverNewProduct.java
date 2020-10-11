package com.tsofen.onthegoshopClient.DriverViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tsofen.onthegoshopClient.Adapters.AddProductVanAdapter;
import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.AllProductsHandler;
import com.tsofen.onthegoshopClient.DataHandlers.NewProductHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.AddProductVanThread;
import com.tsofen.onthegoshopClient.ThreadServices.AllProductsThread;

import java.util.ArrayList;

public class DriverNewProduct extends AppCompatActivity {

    private ListView productsListV;
    private ArrayList<Product> products;
    private AddProductVanAdapter adapter;

    private HandlerThread getStorageHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_new_product);

        productsListV = findViewById(R.id.driveStorageView);

        getStorageHandlerThread = new HandlerThread("getAllProductHandlerThread");
        getStorageHandlerThread.start();
        AllProductsThread allProductsThread = new AllProductsThread(new AllProductsHandler() {
            @Override
            public void onDataReceived(final ArrayList<Product> products) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setListView(products);
                    }
                });
            }
        }, "driver");
        Handler handler = new Handler(getStorageHandlerThread.getLooper());
        handler.post(allProductsThread);
    }

    private void setListView(ArrayList<Product> productArrayList) {
        products = productArrayList;
        adapter = new AddProductVanAdapter(this, products);
        productsListV.setAdapter(adapter);
    }

    public void addProductToVan(View v){
        LinearLayout linearLayout  = (LinearLayout) v.getParent();
        TextView idTV = linearLayout.findViewById(R.id.addProductToVanID);
        Spinner spin = linearLayout.findViewById(R.id.addAmountToPro);
        Product product = new Product();
        product.setId(Integer.parseInt(idTV.getText().toString()));
        product.setAmount(Double.parseDouble(spin.getSelectedItem().toString()));
        AddProductVanThread productVanThread = new AddProductVanThread(String.valueOf(product.getId()),
                String.valueOf(product.getAmount()), new NewProductHandler() {
                    @Override
                    public void onProductAdded() {
                        Toast.makeText(DriverNewProduct.this, "product added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DriverNewProduct.this, VanStorage.class);
                        finishAffinity();
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(DriverNewProduct.this, "Failed to add Product", Toast.LENGTH_SHORT).show();
                    }
                });
        Handler handler = new Handler(getStorageHandlerThread.getLooper());
        handler.post(productVanThread);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (getStorageHandlerThread!=null && getStorageHandlerThread.isAlive())
            getStorageHandlerThread.quit();
    }
}