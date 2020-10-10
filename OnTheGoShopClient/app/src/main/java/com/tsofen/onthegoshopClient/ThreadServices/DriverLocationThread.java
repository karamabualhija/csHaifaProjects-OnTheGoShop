package com.tsofen.onthegoshopClient.ThreadServices;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.tsofen.onthegoshopClient.DataHandlers.DriverLocationHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class DriverLocationThread implements Runnable {

    private static final String TAG = "DriverLocationThread";

    private String driverId;
    private DriverLocationHandler handler;

    public DriverLocationThread(String driverId, DriverLocationHandler handler) {
        this.driverId = driverId;
        this.handler = handler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker =new UrlMaker();
        HashMap<String, String> params = new HashMap<>();
        params.put("van_id", driverId);

        TextDownloader textDownloader = TextDownloader.getInstance();

        textDownloader.getText(urlMaker.createUrl(ServicesName.DriverLocation, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                if (downloadedData!=null){
                    try {
                        JSONObject object = new JSONObject(downloadedData);
                        if (object.has("Latitude") && object.has("Lang")) {
                            LatLng latLng = new LatLng(object.getDouble("Latitude"), object.getDouble("Lang"));
                            handler.onLocationReceived(latLng);
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "onDataDownloadCompleted: json exception: " + e.getMessage());
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
