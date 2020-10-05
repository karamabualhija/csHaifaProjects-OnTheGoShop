package com.tsofen.onthegoshopClient.ThreadServices;

import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.OrderDetailsHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * this is a thread to receive the products that exists in
 * some order and populate the list in the ui with products it
 * received.
 * */

public class OrderDetailsThread implements Runnable {

    OrderDetailsHandler handler;
    int orderID;

    public OrderDetailsThread(OrderDetailsHandler handler, int orderID) {
        this.handler = handler;
        this.orderID = orderID;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        Map<String, String> params = new HashMap<>();
        params.put("orderId", String.valueOf(orderID));

        TextDownloader textDownloader = TextDownloader.getInstance();

        textDownloader.getText(urlMaker.createUrl(ServicesName.OrderDetails, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                try {
                    ArrayList<Product> products = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(downloadedData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Product product = new Product();
                        product.setAmount(object.getDouble("amount"));
                        product.setName(object.getString("name"));
                        product.setPrice((float) object.getDouble("price"));
                        products.add(product);
                    }
                    handler.onProductsReceived(products);
                } catch (JSONException e) {
                    onDownloadError();
                }
            }

            @Override
            public void onDownloadError() {

            }
        });
    }
}
