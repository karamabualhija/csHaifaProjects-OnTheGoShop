package com.tsofen.onthegoshopClient.DataHandlers;

import com.tsofen.onthegoshopClient.Beans.Product;

import java.util.ArrayList;

public interface OrderDetailsHandler {
    void onProductsReceived(ArrayList<Product> products);
}
