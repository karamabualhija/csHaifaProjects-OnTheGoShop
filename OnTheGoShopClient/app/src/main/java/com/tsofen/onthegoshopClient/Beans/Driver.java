package com.tsofen.onthegoshopClient.Beans;

import java.util.ArrayList;

public class Driver {

    String name;
    String phonenumber;
    ArrayList<Order> orders;
    String vanNum;

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
