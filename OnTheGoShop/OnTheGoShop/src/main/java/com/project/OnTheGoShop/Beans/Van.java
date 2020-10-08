package com.project.OnTheGoShop.Beans;

import javax.persistence.*;

import org.json.simple.JSONObject;

import java.util.List;

@Entity(name = "Van")
public class Van {
    int id;
    double lang;
    double latitude;
    List<Product> products;
    List<Order> orders;
    

    public Van(int id/*, double capacity*/, double lang, double latitude, List<Product> products, List<Order> orders) {
		super();
		this.id = id;
//		this.capacity = capacity;
		this.lang = lang;
		this.latitude = latitude;
		this.products = products;
		this.orders = orders;
	}

	public Van() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
//
//    @Column
//    public double getCapacity() {
//        return capacity;
//    }
//
//    public void setCapacity(double capacity) {
//        this.capacity = capacity;
//    }

    @Column
    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    @Column
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @ManyToMany
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @ManyToMany
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

	@SuppressWarnings("unchecked")
	public JSONObject toJson1() {
		   JSONObject jo = new JSONObject();
		   jo.put("id", this.id);
//		   jo.put("capacity", this.capacity);
		   
		   return jo;
		

	}



}