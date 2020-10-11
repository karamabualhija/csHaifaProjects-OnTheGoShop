package com.tsofen.onthegoshopClient.ThreadServices;

import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.DataHandlers.UserOrderHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserOrderThread implements Runnable{

    public static final int WAITING_ORDERS = 1;
    public static final int OLD_ORDERS = 2;
    private int state;
    private UserOrderHandler handler;

    public UserOrderThread(int state, UserOrderHandler handler) {
        this.state = state;
        this.handler = handler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        String url;
        switch (state){
            case WAITING_ORDERS:
                url = urlMaker.createUrl(ServicesName.ActiveOrder, null);
                break;
            case OLD_ORDERS:
                url = urlMaker.createUrl(ServicesName.OldOrders, null);
                break;
            default:
                url = null;
                break;
        }
        if (url!=null){
            TextDownloader textDownloader = TextDownloader.getInstance();
            textDownloader.getText(url, new OnDataReadyHandler() {
                @Override
                public void onDataDownloadCompleted(String downloadedData) {
                    ArrayList<Order> orders = new ArrayList<>();
                    try{
                        JSONArray jsonArray = new JSONArray(downloadedData);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Order order = new Order();
                            order.setId(object.getInt("id"));
                            order.setTotalPrice((float)object.getDouble("price"));
                            orders.add(order);
                        }
                    }
                    catch (Exception e){
                        onDownloadError();
                    }
                    handler.onOrdersReceived(orders);
                }

                @Override
                public void onDownloadError() {

                }
            });
        }
    }
}
