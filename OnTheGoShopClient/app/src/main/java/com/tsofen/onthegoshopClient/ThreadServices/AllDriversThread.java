package com.tsofen.onthegoshopClient.ThreadServices;

import android.util.Log;

import com.tsofen.onthegoshopClient.Beans.Driver;
import com.tsofen.onthegoshopClient.DataHandlers.AllDriverHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllDriversThread implements Runnable {

    private static final String TAG = "AllDriversThread";

    AllDriverHandler allDriverHandler;

    public AllDriversThread(AllDriverHandler allDriverHandler) {
        this.allDriverHandler = allDriverHandler;
    }

    @Override
    public void run() {

        UrlMaker urlMaker = new UrlMaker();

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.AllDrivers, null), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                if(downloadedData!=null){
                    try {
                        ArrayList<Driver> drivers = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(downloadedData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Driver driver = new Driver();
                            if (object.has("name"))
                                driver.setName(object.getString("name"));
                            if (object.has("phonenumber"))
                                driver.setPhonenumber(object.getString("phonenumber"));
                            if (object.has("vanNum"))
                                driver.setVanNum(object.getString("vanNum"));
                            drivers.add(driver);
                        }
                        allDriverHandler.onDriversReceived(drivers);
                    } catch (JSONException e) {
                        Log.e(TAG, "onDataDownloadCompleted: JSON array exception" + e.getMessage());
                    }
                }
            }

            @Override
            public void onDownloadError() {

            }
        });

    }
}
