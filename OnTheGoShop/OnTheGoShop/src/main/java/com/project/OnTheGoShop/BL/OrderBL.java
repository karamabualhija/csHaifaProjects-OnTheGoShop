package com.project.OnTheGoShop.BL;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.OnTheGoShop.Beans.Order;
import com.project.OnTheGoShop.Beans.Product;
import com.project.OnTheGoShop.Repo.OrderRepository;
@Service
public class OrderBL {
	@Autowired
	OrderRepository orderRepository;

	public ArrayList<Order> findallorders() {
		return orderRepository.findAll();
	}

	@SuppressWarnings("unchecked")
	public JSONArray findallorderpro(int orders_id) {
	
		ArrayList<Product> res=(ArrayList<Product>) orderRepository.findById(orders_id).getProducts();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	jsonArray.add(res.get(i).toJson1());
	    }
	    return jsonArray;
	}

	public double findprice(int orders_id) {
		ArrayList<Product> res=(ArrayList<Product>) orderRepository.findById(orders_id).getProducts();
	    double sum=0;
	    for(int i=0;i<res.size();i++)
	    {
	    	sum+=res.get(i).getPrice();
	    }
	    return sum;

	}
	
}
