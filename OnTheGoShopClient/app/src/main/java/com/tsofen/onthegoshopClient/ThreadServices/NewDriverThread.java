package com.tsofen.onthegoshopClient.ThreadServices;

import com.tsofen.onthegoshopClient.DataHandlers.NewDriverHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import java.util.HashMap;

public class NewDriverThread implements Runnable {

    private String name;
    private String phonenumber;
    private String username;
    private String password;
    private int vanNum;
    private NewDriverHandler handler;

    public NewDriverThread(String name, String phonenumber, String username, String password, int vanNum, NewDriverHandler handler) {
        this.name = name;
        this.phonenumber = phonenumber;
        this.username = username;
        this.password = password;
        this.vanNum = vanNum;
        this.handler = handler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        HashMap<String , String> params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("phonenumber", phonenumber);
        params.put("password", password);
        params.put("vanNum", String.valueOf(vanNum));

        TextDownloader textDownloader = TextDownloader.getInstance();

        textDownloader.getText(urlMaker.createUrl(ServicesName.AddDriver, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                handler.onDriverAdded();
            }

            @Override
            public void onDownloadError() {
                handler.onFailure();
            }
        });
    }
}
