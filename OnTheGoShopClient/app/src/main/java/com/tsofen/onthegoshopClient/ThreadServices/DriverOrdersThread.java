package com.tsofen.onthegoshopClient.ThreadServices;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.DataHandlers.DriverOrdersHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DriverOrdersThread implements Runnable {

    private static final String TAG = "DriverOrdersThread";

    private String id;
    private DriverOrdersHandler handler;

    public DriverOrdersThread(String id, DriverOrdersHandler handler) {
        this.id = id;
        this.handler = handler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", id);

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.DriverOrders, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                if (downloadedData!=null){
                    try {
                        JSONArray jsonArray = new JSONArray(downloadedData);
                        ArrayList<Order> orders =new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.has("id") && object.has("price") && object.has("lat") && object.has("lan")){
                                Order order = new Order();
                                order.setLatLng(new LatLng(object.getDouble("lat"), object.getDouble("lan")));
                                order.setTotalPrice((float) object.getDouble("price"));
                                order.setId(object.getInt("id"));
                                orders.add(order);
                            }
                        }
                        handler.onOrdersReceived(orders);
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
