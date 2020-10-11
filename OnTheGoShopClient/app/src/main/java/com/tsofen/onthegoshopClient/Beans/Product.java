package com.tsofen.onthegoshopClient.Beans;


import java.io.Serializable;

public class Product implements Serializable {

    int id;
    String name;
    double amount;
    float price;
    private int ImageResId;

    public Product(String name, double amount, float price) {
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    public Product() {
    }

    public int getImageResId() {
        return ImageResId;
    }

    public void setImageResId(int imageResId) {
        ImageResId = imageResId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }
}
