package com.project.OnTheGoShop.Beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User extends Person {
    List<Order> Orders;
    double lan;
    double lat;
    String cardNum;

    @OneToMany
    public List<Order> getOrders() {

        return Orders;
    }

    public void setPendingOrder(List<Order> pendingOrder) {
        this.Orders = pendingOrder;
    }


    @Column
    public double getLan() {
        return lan;
    }

    public void setLan(double lan) {
        this.lan = lan;
    }

    @Column
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Column
    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

}
