package com.project.OnTheGoShop.BL;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.OnTheGoShop.Beans.Order;
import com.project.OnTheGoShop.Beans.Product;
import com.project.OnTheGoShop.Beans.order_product;
import com.project.OnTheGoShop.Repo.OrderRepository;
import com.project.OnTheGoShop.Repo.Order_ProductRepository;
import com.project.OnTheGoShop.Repo.ProductRepository;
@Service
public class OrderBL {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	Order_ProductRepository oderprorepo;
	@Autowired
	ProductRepository prorepo;
	

	public ArrayList<Order> findallorders() {
		return orderRepository.findAll();
	}

	@SuppressWarnings("unchecked")
	public JSONArray findallorderpro(int orders_id) {
	
		ArrayList<order_product> res= oderprorepo.findAllByOrderid(orders_id);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	int productid=res.get(i).getProductid();
	    	Product pro=prorepo.findById(productid);
	    	jsonArray.add(pro.toJson1());
	    }
	    return jsonArray;
	}

	public float findprice(int orders_id) {
		ArrayList<order_product> res= oderprorepo.findAllByOrderid(orders_id);
	    float sum=0;
	    for(int i=0;i<res.size();i++)
	    {
	    	int productid=res.get(i).getProductid();
	    	Product pro=prorepo.findById(productid);
	    	int amount=res.get(i).getAmount();
	    	Float price=pro.getPrice();
	    	sum+=(amount*price);
	    }
	    return sum;

	}

	public Order fondorder(int orders_id) {
	return orderRepository.findById(orders_id);
	}
	
}