package com.tsofen.onthegoshopClient.ThreadServices;

import com.google.android.gms.maps.model.LatLng;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.DataHandlers.UserOrderHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class UserOrderThread implements Runnable{

    public static final int WAITING_ORDERS = 1;
    public static final int OLD_ORDERS = 2;
    public static final int All_ORDERS = 3;
    private int state;
    private String username;
    private UserOrderHandler handler;
    private int id;

    public UserOrderThread(int state, int id, UserOrderHandler handler) {
        this.state = state;
        this.handler = handler;
        this.id = id;
    }

    public UserOrderThread(int state, String username, UserOrderHandler handler) {
        this.state = state;
        this.username = username;
        this.handler = handler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        String url;
        HashMap<String, String> params = new HashMap<>();
        switch (state){
            case WAITING_ORDERS:
                params.put("userId", String.valueOf(id));
                url = urlMaker.createUrl(ServicesName.ActiveOrder, params);
                break;
            case OLD_ORDERS:
                params.put("userId", String.valueOf(id));
                url = urlMaker.createUrl(ServicesName.OldOrders, params);
                break;
            case All_ORDERS:
                params.put("username", username);
                url = urlMaker.createUrl(ServicesName.getUserOrders, params);
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
                            order.setLatLng(new LatLng(object.getDouble("lat"), object.getDouble("lan")));
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
