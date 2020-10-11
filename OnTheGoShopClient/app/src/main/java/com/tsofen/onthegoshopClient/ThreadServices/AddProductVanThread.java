package com.tsofen.onthegoshopClient.ThreadServices;

import com.tsofen.onthegoshopClient.DataHandlers.NewProductHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import java.util.HashMap;

public class AddProductVanThread implements Runnable {

    private String id;
    private String amount;
    private NewProductHandler newProductHandler;

    public AddProductVanThread(String id, String amount, NewProductHandler newProductHandler) {
        this.id = id;
        this.amount = amount;
        this.newProductHandler = newProductHandler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        HashMap<String, String > params = new HashMap<>();
        params.put("id", id);
        params.put("amount", amount);

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.AddProductVan, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                newProductHandler.onProductAdded();
            }

            @Override
            public void onDownloadError() {
                newProductHandler.onFailure();
            }
        });
    }
}
