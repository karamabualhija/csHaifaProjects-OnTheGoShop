package com.project.OnTheGoShop.Beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
@Entity(name = "van_products")
public class van_products {
	int id;
	int vanid;
	int productid;
	int amount;
	public van_products(int id, int vanid, int product_id, int amount) {
		super();
		this.id = id;
		this.vanid = vanid;
		this.productid = product_id;
		this.amount = amount;
	}
	public van_products( int vanid, int product_id, int amount) {
		super();
		this.vanid = vanid;
		this.productid = product_id;
		this.amount = amount;
	}
	public van_products() {
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
	public int getProductid() {
		return productid;
	}
	@Column
	public int getVanid() {
		return vanid;
	}
	public void setVanid(int vanid) {
		this.vanid = vanid;
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
