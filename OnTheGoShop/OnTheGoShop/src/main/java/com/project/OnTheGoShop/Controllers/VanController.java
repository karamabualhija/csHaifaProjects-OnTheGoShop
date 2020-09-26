package com.project.OnTheGoShop.Controllers;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.OnTheGoShop.BL.VanBL;
import com.project.OnTheGoShop.Beans.Order;
import com.project.OnTheGoShop.Beans.Product;
import com.project.OnTheGoShop.Beans.Van;
@RestController
@RequestMapping("Van")
public class VanController {
	@Autowired
	VanBL vanbl;

	@SuppressWarnings("unchecked")
	@GetMapping("AllVans")
	JSONArray getAllVans()
	{	/* all vans: id
		number
		capacity*/
		ArrayList<Van> res=vanbl.findallvans();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	jsonArray.add(res.get(i).toJson1());
	    }
	    return jsonArray;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("getVanStorage")
	JSONArray getVanStorage(HttpSession session)
	{	/*
		products that is in the van
		id 
		name 
		amount 
		*/
		int id=(int) session.getAttribute("id");
		Van res=vanbl.findvan(id);
		ArrayList<Product> products=(ArrayList<Product>) res.getProducts();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<products.size();i++)
	    {
	    	jsonArray.add(products.get(i).toJson1());
	    }
	    return jsonArray;


		
	}

	@GetMapping("getVanOrdere")
	JSONArray getVanOrdere(HttpSession session)
	{
		/*
		orders_id
		orders_price
		*/
		int id=(int) session.getAttribute("id");
		Van res=vanbl.findvan(id);
		ArrayList<Order> orders=(ArrayList<Order>) res.getOrders();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<orders.size();i++)
	    {
	    	jsonArray.add(orders.get(i).toJson1());
	    }
	    return jsonArray;
		
	}
	/*@GetMapping("getVanLocatoin")
	Location getVanLocatoin(int van_id)
	{
		
	}*/

	

}
