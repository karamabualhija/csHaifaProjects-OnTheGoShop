package com.tsofen.onthegoshopClient.Beans;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    int id;
    List<Product> products;
    float totalPrice;
    LatLng latLng;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", latLng=" + latLng +
                ", product size" + products.size() +
                '}';
    }
}
