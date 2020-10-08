package com.tsofen.onthegoshopClient.UserViews;

import com.tsofen.onthegoshopClient.Adapters.Cart_ProductAdapter;
import com.tsofen.onthegoshopClient.Beans.Product;

import java.util.ArrayList;

public class CartListAdapters {

    ArrayList<Product> products;
    Cart_ProductAdapter cartAdapter;
    private static CartListAdapters cartListAdapters;

    private CartListAdapters(){}

    public static CartListAdapters getInstance() {
        if (cartListAdapters == null){
            cartListAdapters = new CartListAdapters();
        }
        return cartListAdapters;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setCartAdapter(Cart_ProductAdapter cartAdapter) {
        this.cartAdapter = cartAdapter;
    }

    public Cart_ProductAdapter getCartAdapter() {
        return cartAdapter;
    }

}
