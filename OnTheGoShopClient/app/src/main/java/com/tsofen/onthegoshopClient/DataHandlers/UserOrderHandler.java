package com.tsofen.onthegoshopClient.DataHandlers;

import com.tsofen.onthegoshopClient.Beans.Order;

import java.util.ArrayList;

public interface UserOrderHandler {
    void onOrdersReceived(ArrayList<Order> orders);
}
