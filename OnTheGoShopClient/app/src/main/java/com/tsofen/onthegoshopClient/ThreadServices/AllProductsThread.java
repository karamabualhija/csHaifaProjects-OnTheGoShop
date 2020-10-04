package com.tsofen.onthegoshopClient.ThreadServices;

import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.AllProductsHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllProductsThread implements Runnable{

    AllProductsHandler handler;

    public AllProductsThread(AllProductsHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.AllProducts, null), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                ArrayList<Product> products = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(downloadedData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Product product = new Product(
                                object.getString("name"),
                                object.getDouble("amount"),
                                (float) object.getDouble("price")
                        );
                        products.add(product);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.onDataReceived(products);
            }

            @Override
            public void onDownloadError() {

            }
        });
    }
}
