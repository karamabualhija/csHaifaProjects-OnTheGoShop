package com.project.OnTheGoShop.Controllers;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OnTheGoShop.BL.OrderBL;
import com.project.OnTheGoShop.BL.VanBL;
import com.project.OnTheGoShop.Beans.Order;
import com.project.OnTheGoShop.Beans.Product;
import com.project.OnTheGoShop.Beans.Van;
@RestController
@RequestMapping("Van")
public class VanController {
	@Autowired
	VanBL vanbl;

	@Autowired
	OrderBL orderbl;

	@SuppressWarnings("unchecked")
	@GetMapping("AllVans")
	JSONArray getAllVans()
	{	/* all vans: id
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
	JSONArray getVanStorage(@RequestParam int id)
	{	/*
		products that is in the van
		id 
		name 
		amount 
		*/
//		int id=(int) session.getAttribute("id");
		Van res=vanbl.findvan(id);
		ArrayList<Product> products=(ArrayList<Product>) res.getProducts();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<products.size();i++)
	    {
	    	jsonArray.add(products.get(i).toJson1());
	    }
	    return jsonArray;


		
	}

	@SuppressWarnings("unchecked")
	@GetMapping("getVanOrders")
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
	    	int orid=orders.get(i).getId();
		   JSONObject jo = new JSONObject();
		   jo.put("id", orid);
		   jo.put("price",orderbl.findprice(id) );
		   jsonArray.add(jo);
	    }
	    return jsonArray;
		
	}
	@SuppressWarnings("unchecked")
	@GetMapping("getVanLocatoin")
	JSONObject getVanLocatoin(@RequestParam int van_id)
	{	   
	   Van v=vanbl.findvan(van_id);
	   JSONObject jo = new JSONObject();
	   jo.put("Lang", v.getLang());
	   jo.put("Latitude",v.getLatitude());
	   return jo;

		
	}
	@GetMapping("updatelocation")
	void updatelocation(@RequestParam String lan,@RequestParam String lag,@RequestParam int id)
	{	   
	  vanbl.updatelocation(lag,lan,id);
	 		
	}

	

}
