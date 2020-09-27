package com.project.OnTheGoShop.BL;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.OnTheGoShop.Beans.Order;
import com.project.OnTheGoShop.Repo.OrderRepository;

public class OrderBL {
	@Autowired
	OrderRepository orderRepository;

	public ArrayList<Order> findallorders() {
		return orderRepository.findAll();
	}
	
}
