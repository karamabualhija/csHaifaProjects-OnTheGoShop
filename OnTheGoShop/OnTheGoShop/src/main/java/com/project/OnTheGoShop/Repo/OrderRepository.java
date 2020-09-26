package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Order;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Integer> {
	public ArrayList<Order> findAll();
}
