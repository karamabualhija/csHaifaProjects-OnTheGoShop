package com.tsofen.onthegoshopClient.ThreadServices;

import com.tsofen.onthegoshopClient.DataHandlers.SetOrderDeliveredHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import java.util.HashMap;

public class SetOrderDeliveredThread implements Runnable {

    private int id;
    private SetOrderDeliveredHandler handler;

    public SetOrderDeliveredThread(int id, SetOrderDeliveredHandler handler) {
        this.id = id;
        this.handler = handler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        HashMap<String, String> params = new HashMap<>();
        params.put("id", String.valueOf(id));

        TextDownloader textDownloader =TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.SetOrderDel, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                handler.onOrderChanged();
            }

            @Override
            public void onDownloadError() {
                handler.onFailure();
            }
        });
    }
}
