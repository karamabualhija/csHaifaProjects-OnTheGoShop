package com.project.OnTheGoShop.Beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity(name = "Order_Product")
public class order_product {
	int id;
	int orderid;
	int productid;
	int amount;
	public order_product(int id, int order_id, int product_id, int amount) {
		super();
		this.id = id;
		this.orderid = order_id;
		this.productid = product_id;
		this.amount = amount;
	}
	public order_product() {
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
	@Column
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	@Column
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	@Column
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	

}
