package com.tsofen.onthegoshopClient.DataHandlers;

import com.tsofen.onthegoshopClient.Beans.Order;

import java.util.ArrayList;

public interface AllOrdersHandler {
    void onOrdersReceived(ArrayList<Order> orders);
    void onFailure();
}
