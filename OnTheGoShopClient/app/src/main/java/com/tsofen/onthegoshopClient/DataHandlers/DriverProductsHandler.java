package com.tsofen.onthegoshopClient.DataHandlers;

import com.tsofen.onthegoshopClient.Beans.Product;

import java.util.ArrayList;

public interface DriverProductsHandler {
    void onProductsReceived(ArrayList<Product> productArrayList);
    void onFailure();
}
