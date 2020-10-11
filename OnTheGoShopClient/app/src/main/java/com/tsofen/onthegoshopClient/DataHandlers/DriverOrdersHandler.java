package com.tsofen.onthegoshopClient.DataHandlers;

import com.tsofen.onthegoshopClient.Beans.Order;

import java.util.ArrayList;

public interface DriverOrdersHandler {
    void onOrdersReceived(ArrayList<Order> orderArrayList);
    void onFailure();
}
