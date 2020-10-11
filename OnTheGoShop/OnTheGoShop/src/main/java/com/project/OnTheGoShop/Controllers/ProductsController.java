package com.project.OnTheGoShop.Controllers;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OnTheGoShop.BL.ProductBL;
import com.project.OnTheGoShop.Beans.Product;

@RestController
@RequestMapping("Product")
public class ProductsController {
	@Autowired
	ProductBL productbl;

	@SuppressWarnings("unchecked")
	@GetMapping("AllProducts")
	JSONArray getAllProducts()
	{
		/* all products:
		img
		name
		price*/
		ArrayList<Product> res=productbl.findallproducts();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	jsonArray.add(res.get(i).toJson2(1));
	    }
	    return jsonArray;
		
	
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("ProductsByPrice")
	JSONArray getProductsByPrice(@RequestParam float min,@RequestParam float max)
	{
		/* all products: img
		name
		price*/
		ArrayList<Product> res=productbl.findallprice(min,max);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	jsonArray.add(res.get(i).toJson2(1));
	    }
	    return jsonArray;
		
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("ProductsContains")
	JSONArray getProductsContains(@RequestParam String str)
	{
		/* all products: img
		name
		price*/
		ArrayList<Product> res=productbl.findallcontains(str);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	jsonArray.add(res.get(i).toJson2(1));
	    }
	    return jsonArray;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("AllProductsManger")
	JSONArray getAllProductsManager()
	{
		/* all products: img
		name
		price
		id
		amount*/
		ArrayList<Product> res=productbl.findallproducts();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	jsonArray.add(res.get(i).toJson2(2));
	    }
	    return jsonArray;
	}
	/*

	*/
	@GetMapping("AddNewProduct")
	void newProduct(@RequestParam String name, @RequestParam Float price,@RequestParam int amount)
	{
		Product p=new Product( name,  amount,  price) ;
		productbl.add(p);
	}
}