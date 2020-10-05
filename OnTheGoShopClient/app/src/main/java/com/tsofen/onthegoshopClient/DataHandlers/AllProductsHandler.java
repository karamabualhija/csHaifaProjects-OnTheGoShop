package com.tsofen.onthegoshopClient.DataHandlers;

import com.tsofen.onthegoshopClient.Beans.Product;

import java.util.ArrayList;

public interface AllProductsHandler {
     void onDataReceived(ArrayList<Product> products);
}
