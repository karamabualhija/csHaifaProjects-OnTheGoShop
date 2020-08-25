package com.project.OnTheGoShop.Beans;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Van")
public class Van {
	int id;
	double capacity;
	double lang;
	double latitude;
	List<Product> products;
	List<Order> orders;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column
	public double getCapacity() {
		return capacity;
	}
	public void setCapacity(double capacity) {
		this.capacity = capacity;
	}
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
	
	
}
