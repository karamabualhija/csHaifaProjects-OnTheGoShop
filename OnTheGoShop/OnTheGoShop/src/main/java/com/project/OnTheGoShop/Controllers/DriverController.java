package com.project.OnTheGoShop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OnTheGoShop.BL.DriverBL;
import com.project.OnTheGoShop.BL.OrderBL;
import com.project.OnTheGoShop.BL.VanBL;
import com.project.OnTheGoShop.Beans.Driver;
import com.project.OnTheGoShop.Beans.Order;
import com.project.OnTheGoShop.Beans.Sort;
import com.project.OnTheGoShop.Beans.Van;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.simple.*;

@RestController
@RequestMapping("Driver")
public class DriverController {
	@Autowired
	DriverBL driverbl;
	@Autowired
	VanBL vanbl;
	@Autowired
	OrderBL orderbl;
	
	@GetMapping("RegisterDriver")
	String RegisterDriver(@RequestParam String name,@RequestParam String phonenumber,@RequestParam String username,@RequestParam String password,@RequestParam int vanNum)
	{
		Van van=vanbl.findvan(vanNum);
		if (van!=null)
			return "please change van num";
		van=new Van(vanNum);
		Driver d1=driverbl.findbuusername(username);
		if(d1!=null)
			return "please change the username and try again";
		Driver d=new Driver( van, name,  username,  password,  phonenumber);
		driverbl.add(d);
		return"success";
	}
	@SuppressWarnings("unchecked")
	@GetMapping("getAllDrivers")
	JSONArray getAllDrivers()
	{
		ArrayList<Driver> res=driverbl.findalldrivers();
		System.err.println(res.size());
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	jsonArray.add(res.get(i).toJson());
	    }
	    return jsonArray;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("getdriverorders")
	JSONArray getorders(@RequestParam int id)
	{
		Driver d=driverbl.finddriver(id);
		Van res=d.getVan();
		ArrayList<Order> orders=(ArrayList<Order>) res.getOrders();
		Sort.Updatedistances(orders,res.getLang(),res.getLatitude());
		Collections.sort(orders);
//		orders=sortbydis(orders,res.getLatitude(),res.getLang());
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<orders.size();i++)
	    {
	    	int orid=orders.get(i).getId();
		   JSONObject jo = new JSONObject();
		   jo.put("id", orid);
		   jo.put("price",orderbl.findprice(id) );
		   jo.put("lan", orders.get(i).getLan());
		   jo.put("lat", orders.get(i).getLat());
		   jsonArray.add(jo);
	    }
	    return jsonArray;
	}
	
	   @GetMapping("orderreceived")
		void orderreceived(@RequestParam int order_id,@RequestParam int driver_id)
		{
		   orderbl.updatepen(order_id);
		   int van_id=driverbl.finddriver(driver_id).getVan().getId();
		   vanbl.orderreceived(van_id,order_id);
		   
		}
////	private ArrayList<Order> sortbydis(ArrayList<Order> orders, String lat, String lang) {
//////		sort soreted=new sort(orders,lat,lang);
//////		 Collections.sort((List<sort>) soreted);
//////		 return soreted.getOrders();
//
//	}

	

}

