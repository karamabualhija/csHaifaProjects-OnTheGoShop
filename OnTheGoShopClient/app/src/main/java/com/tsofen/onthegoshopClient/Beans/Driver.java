package com.tsofen.onthegoshopClient.Beans;

import java.util.ArrayList;

public class Driver {

    String name;
    String phonenumber;
    String username;
    ArrayList<Order> orders;
    String vanNum;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVanNum() {
        return vanNum;
    }

    public void setVanNum(String vanNum) {
        this.vanNum = vanNum;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
