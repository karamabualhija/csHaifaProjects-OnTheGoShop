package com.project.OnTheGoShop.Beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.json.simple.JSONObject;

@Entity(name = "Product")
public class Product {

    int id;
    String name;
    int amount;
    float price;
//    String img;
    
    public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(String name, int amount, float price) {
		super();
		this.name = name;
		this.amount = amount;
		this.price = price;
	}

    public Product(int id, String name, int amount, float price) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.price = price;
    }

    //	@Column
//    public String getImg() {
//		return img;
//	}
//
//	public void setImg(String img) {
//		this.img = img;
//	}

	@Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Column
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

	@SuppressWarnings("unchecked")
	public JSONObject toJson1() {
		   JSONObject jo = new JSONObject();
		   jo.put("id", this.id);
		   jo.put("name", this.name);
		   jo.put("amount", this.amount);
		   jo.put("price", this.price);
		   return jo;
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJson2(int flag) {
		   JSONObject jo = new JSONObject();
//		   jo.put("img", this.img);
		   jo.put("name", this.name);
		   jo.put("price", this.price);
		   jo.put("amount", this.amount);
		   jo.put("id", this.id);
		   return jo;
	}


}