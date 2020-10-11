package com.tsofen.onthegoshopClient.ThreadServices;

import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.AllProductsHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AllProductsThread implements Runnable{

    AllProductsHandler handler;
    String type;

    public AllProductsThread(AllProductsHandler handler, String type) {
        this.handler = handler;
        this.type = type;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        String url;

        if(type == null)
            return;
        if (type.equals("user"))
            url = urlMaker.createUrl(ServicesName.AllProducts, null);
        else
            url = urlMaker.createUrl(ServicesName.ManagerAllProduct, null);

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(url, new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                ArrayList<Product> products = new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(downloadedData);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Product product = new Product();
                        if (object.has("amount"))
                            product.setAmount(object.getDouble("amount"));
                        if (object.has("name"))
                            product.setName(object.getString("name"));
                        if (object.has("price"))
                            product.setPrice((float) object.getDouble("price"));
                        if (object.has("id"))
                            product.setId(object.getInt("id"));
                        products.add(product);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.onDataReceived(products);
            }

            @Override
            public void onDownloadError() {

            }
        });
    }
}
