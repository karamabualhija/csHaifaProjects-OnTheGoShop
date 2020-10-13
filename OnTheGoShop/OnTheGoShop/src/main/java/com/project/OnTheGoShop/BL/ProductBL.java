package com.project.OnTheGoShop.BL;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.OnTheGoShop.Beans.Product;
import com.project.OnTheGoShop.Repo.ProductRepository;
@Service
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
		List<Product> products = productRepository.findByName(p.getName());
		if (products.size()==1){
			productRepository.updatestorage(products.get(0).getId(), products.get(0).getAmount() + p.getAmount());
			return;
		}
		productRepository.save(p);
		
	}

	public Product findpro(int pro_id) {
		return productRepository.findById(pro_id);
	}

	public void updatestorage(int pro_id, int amount) {
		productRepository.updatestorage(pro_id,  amount)	;
	}
	

}