package com.tsofen.onthegoshopClient.ThreadServices;

import android.util.Log;

import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.DriverProductsHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DriverProductsThread implements Runnable {

    private static final String TAG = "DriverProductsThread";

    private String id;
    private DriverProductsHandler handler;

    public DriverProductsThread(String id, DriverProductsHandler handler) {
        this.id = id;
        this.handler = handler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.DriverProduct, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                if (downloadedData!=null){
                    try {
                        JSONArray jsonArray = new JSONArray(downloadedData);
                        ArrayList<Product> products =new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.has("price") && object.has("name") && object.has("amount")){
                                Product product = new Product();
                                product.setPrice((float) object.getDouble("price"));
                                product.setName(object.getString("name"));
                                product.setAmount(object.getDouble("amount"));
                                products.add(product);
                            }
                        }
                        handler.onProductsReceived(products);
                    } catch (JSONException e) {
                        Log.d(TAG, "onDataDownloadCompleted: jsonException: " + e.getMessage());
                        handler.onFailure();
                    }
                }
            }

            @Override
            public void onDownloadError() {
                handler.onFailure();
            }
        });
    }
}
