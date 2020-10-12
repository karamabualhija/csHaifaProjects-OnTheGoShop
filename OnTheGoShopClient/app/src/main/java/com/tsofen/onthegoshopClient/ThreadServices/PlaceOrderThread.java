package com.tsofen.onthegoshopClient.ThreadServices;

import com.tsofen.onthegoshopClient.DataHandlers.PlaceOrderHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import java.util.HashMap;

public class PlaceOrderThread implements Runnable {

    String jsonstrinf;
    int user_id;
    String lat;
    String lon;
    PlaceOrderHandler handler;

    public PlaceOrderThread(String jsonstrinf, int user_id, String lat, String lon, PlaceOrderHandler placeOrderHandler) {
        this.jsonstrinf = jsonstrinf;
        this.user_id = user_id;
        this.lat = lat;
        this.lon = lon;
        handler = placeOrderHandler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        HashMap<String, String> params = new HashMap<>();
        params.put("user_id", String.valueOf(user_id));
        params.put("lat", lat);
        params.put("lon", lon);
        params.put("jsonstrinf", jsonstrinf);

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.PlaceOrder, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                handler.onOrderPlaced();
            }

            @Override
            public void onDownloadError() {
                handler.onFailure();
            }
        });
    }
}
