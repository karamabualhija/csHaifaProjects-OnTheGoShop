package com.project.OnTheGoShop.Beans;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Orders")
public class Order {

	int id;
	List<Product> products;
	float totalPrice;
	float totalWeight;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ManyToMany
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	@Column
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	@Column
	public float getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(float totalWeight) {
		this.totalWeight = totalWeight;
	}
	
}
