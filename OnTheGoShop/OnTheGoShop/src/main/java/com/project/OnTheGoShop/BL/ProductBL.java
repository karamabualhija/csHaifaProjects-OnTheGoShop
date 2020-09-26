package com.project.OnTheGoShop.BL;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.OnTheGoShop.Beans.Product;
import com.project.OnTheGoShop.Repo.ProductRepository;

public class ProductBL {
	@Autowired
	ProductRepository productRepository;

	public ArrayList<Product> findallproducts() {
		return productRepository.findAll();
	}

	public ArrayList<Product> findallprice(float min, float max) {
		return productRepository.findByPriceBetween(min,max);

	}

	public ArrayList<Product> findallcontains(String str) {
		// TODO Auto-generated method stub
		return productRepository.findByNameContaining(str);
	}

	public void add(Product p) {
		productRepository.save(p);
		
	}
	

}
