package com.tsofen.onthegoshopClient.ThreadServices;

import android.util.Log;

import com.tsofen.onthegoshopClient.DataHandlers.NewProductHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import java.util.HashMap;


public class NewProductThread implements Runnable {

    private static final String TAG = "NewProductThread";

    private String name;
    private Float price;
    private int amount;
    private NewProductHandler handle;

    public NewProductThread(String name, Float price, int amount, NewProductHandler handle) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.handle = handle;
    }

    @Override
    public void run() {
        UrlMaker urlMaker =new UrlMaker();
        HashMap<String , String> params = new HashMap<>();

        params.put("name", name);
        params.put("price", String.valueOf(price));
        params.put("amount", String.valueOf(amount));

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.AddProduct, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                handle.onProductAdded();
            }

            @Override
            public void onDownloadError() {
                Log.d(TAG, "onDownloadError: failed to add the product");
                handle.onFailure();
            }
        });
    }
}
