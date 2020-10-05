package com.project.OnTheGoShop.Beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import java.util.List;

@Entity
public class User extends Person {
    List<Order> Orders;
    double lan;
    double lat;
    String cardNum;
    

    public User(List<Order> orders, double lan, double lat, String cardNum) {
		super();
		Orders = orders;
		this.lan = lan;
		this.lat = lat;
		this.cardNum = cardNum;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}


	public void setOrders(List<Order> orders) {
		Orders = orders;
	}

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

	@Override
	public void updatesession(HttpSession session) {
		   session.setAttribute("name", this.name);
		   session.setAttribute("username", this.username);
		   session.setAttribute("phonemumber", this.phonenumber);
		   session.setAttribute("type", "user");
		
	}

	public JSONObject toJson()
	{
		   JSONObject jo = new JSONObject();
		   jo.put("name", this.name);
		   jo.put("username", this.username);
		   jo.put("phone", this.phonenumber);

		   
		   return jo;
	}
}
