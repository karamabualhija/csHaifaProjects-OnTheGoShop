package com.tsofen.onthegoshopClient.ThreadServices;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.DataHandlers.AllOrdersHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllOrdersThread implements Runnable {

    private static final String TAG = "AllOrdersThread";

    private AllOrdersHandler allOrdersHandler;

    public AllOrdersThread(AllOrdersHandler allOrdersHandler) {
        this.allOrdersHandler = allOrdersHandler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();

        TextDownloader textDownloader = TextDownloader.getInstance();

        textDownloader.getText(urlMaker.createUrl(ServicesName.AllOrders, null), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                if (downloadedData!=null){
                    try {
                        JSONArray jsonArray = new JSONArray(downloadedData);
                        ArrayList<Order> orders = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.has("id") && object.has("lat") && object.has("lan") &&
                                object.has("price")){
                                Order order = new Order();
                                order.setId(object.getInt("id"));
                                order.setTotalPrice((float) object.getDouble("price"));
                                order.setLatLng(new LatLng(object.getDouble("lat"), object.getDouble("lan")));
                                orders.add(order);
                            }
                        }
                        allOrdersHandler.onOrdersReceived(orders);
                    } catch (JSONException e) {
                        Log.d(TAG, "onDataDownloadCompleted: json exception: " + e.getMessage());
                    }

                }
            }

            @Override
            public void onDownloadError() {
                Log.d(TAG, "onDownloadError: there was a download error");
            }
        });
    }
}
