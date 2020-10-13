package com.tsofen.onthegoshopClient.ThreadServices;

import android.util.Log;

import com.tsofen.onthegoshopClient.Beans.User;
import com.tsofen.onthegoshopClient.DataHandlers.AllUsersHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllUsersThread implements Runnable {

    private static final String TAG = "AllUsersThread";

    private AllUsersHandler allUsersHandler;

    public AllUsersThread(AllUsersHandler allUsersHandler) {
        this.allUsersHandler = allUsersHandler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker=new UrlMaker();

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.AllUsers, null), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                if (downloadedData!=null){
                    try{
                        ArrayList<User> users = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(downloadedData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            User user = new User();
                            if (object.has("name"))
                                user.setName(object.getString("name"));
                            if (object.has("phone"))
                                user.setPhonenumber(object.getString("phone"));
                            if (object.has("username"))
                                user.setUsername(object.getString("username"));
                            users.add(user);
                        }
                        allUsersHandler.onUserReceived(users);
                    } catch (JSONException e) {
                        Log.d(TAG, "onDataDownloadCompleted: exception from JSON: " + e.getMessage());
                        allUsersHandler.onUsersFailure();
                    }
                }
            }

            @Override
            public void onDownloadError() {
                allUsersHandler.onUsersFailure();
            }
        });

    }
}
