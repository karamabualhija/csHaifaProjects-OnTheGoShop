package com.tsofen.onthegoshopClient.ThreadServices;

import android.util.Log;

import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import java.util.HashMap;

public class UpdateDriverLocationThread implements Runnable {

    private static final String TAG = "UpdateDriverLocationThr";

    private String vanNum;
    private double lon;
    private double lat;

    public UpdateDriverLocationThread(String vanNum, double lon, double lat) {
        this.vanNum = vanNum;
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public void run() {
        UrlMaker urlMaker =new UrlMaker();
        HashMap<String, String> params = new HashMap<>();
        params.put("lan", String.valueOf(lon));
        params.put("lag", String.valueOf(lat));
        params.put("id", vanNum);

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.UpdateDriverLocation, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                Log.d(TAG, "onDataDownloadCompleted: the location was updated");
            }

            @Override
            public void onDownloadError() {
                Log.d(TAG, "onDownloadError: there was a failure to update the location");
            }
        });

    }
}
