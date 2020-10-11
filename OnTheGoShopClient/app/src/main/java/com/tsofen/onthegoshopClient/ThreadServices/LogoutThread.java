package com.tsofen.onthegoshopClient.ThreadServices;

import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

public class LogoutThread implements Runnable {


    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.LogOut, null), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {

            }

            @Override
            public void onDownloadError() {

            }
        });
    }
}
