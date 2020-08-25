package com.project.OnTheGoShop.Beans;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity(name="Users")
public class User extends Person {
	List<Order> pendingOrder;
	List<Order> oldOrder;
	double lan;
	double lat;
	String cardNum;

	@OneToMany
	public List<Order> getPendingOrder() {

		return pendingOrder;
	}
	public void setPendingOrder(List<Order> pendingOrder) {
		this.pendingOrder = pendingOrder;
	}
	@OneToMany
	public List<Order> getOldOrder() {
		return oldOrder;
	}
	public void setOldOrder(List<Order> oldOrder) {
		this.oldOrder = oldOrder;
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
