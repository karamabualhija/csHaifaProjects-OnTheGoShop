package com.project.OnTheGoShop.BL;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.OnTheGoShop.Repo.OrderRepository;

public class OrderBL {
	@Autowired
	OrderRepository orderRepository;
	
}
