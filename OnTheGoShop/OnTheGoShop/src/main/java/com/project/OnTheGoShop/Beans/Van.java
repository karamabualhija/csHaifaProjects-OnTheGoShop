package com.project.OnTheGoShop.Beans;

import javax.persistence.*;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity(name = "Van")
public class Van {
    int id;
    String lang;
    String latitude;
//    List<Product> products;
    List<Order> orders;
    

    public Van(int id/*, double capacity*/, String lang, String latitude, List<Order> orders) {
		super();
		this.id = id;
//		this.capacity = capacity;
		this.lang = lang;
		this.latitude = latitude;
//		this.products = products;
		this.orders = orders;
	}

	public Van() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Van(int id) {
		this.id=id;
		lang="";
		latitude="";
//		products=new ArrayList();
		orders=new ArrayList();
		
		
	}

	@Id
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
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Column
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

//    public List<Product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<Product> products) {
//        this.products = products;
//    }

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
//	public void addpro(Product p) {
//		this.products.add(p);
//		
//	}
	public void addorder(Order o) {
		this.orders.add(o);
		
	}


	public void removeorder(int order_id) {
        Iterator<Order> itr = this.orders.iterator();
        while (itr.hasNext()) {
            Order or = itr.next();
            if (or.getId() == order_id) {
                itr.remove();
            }
        }
		
		
	}



}