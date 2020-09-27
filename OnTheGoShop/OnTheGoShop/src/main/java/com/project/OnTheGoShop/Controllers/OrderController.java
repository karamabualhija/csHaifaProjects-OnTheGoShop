package com.project.OnTheGoShop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OnTheGoShop.BL.OrderBL;
import com.project.OnTheGoShop.BL.UserBL;
import com.project.OnTheGoShop.Beans.Driver;
import com.project.OnTheGoShop.Beans.Order;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.json.simple.*;

@RestController
@RequestMapping("Order")
public class OrderController {
	@Autowired
	OrderBL orderbl;
	@Autowired
	UserBL userbl;
	
	@GetMapping("ActiveOrders")
	JSONArray getActiveOrders(HttpSession session)
	{	/*
		return active_orders:
		orders_id
		orders_price
		*/
		//ArrayList<Order> res=orderbl.findallorders();
		int sys_id=(int) session.getAttribute("id");
		ArrayList<Order> res=(ArrayList<Order>) userbl.finduserorders(sys_id);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size()&&res.get(i).isPending();i++)
	    {
	    	jsonArray.add(res.get(i).toJson1());
	    }
	    return jsonArray;
		
	}
	@GetMapping("OldOrders")
	JSONArray getOldOrders(HttpSession session)
	{	/*
		return old_orders:
		orders_id
		orders_price
		*/
	//	ArrayList<Order> res=orderbl.findallorders();
		int sys_id=(int) session.getAttribute("id");
		ArrayList<Order> res=(ArrayList<Order>) userbl.finduserorders(sys_id);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size()&&!(res.get(i).isPending());i++)
	    {
	    	jsonArray.add(res.get(i).toJson1());
	    }
	    return jsonArray;
	}
	@GetMapping("getUserOrders")
	JSONArray getUserOrders(@RequestParam String username)
	{
		ArrayList<Order> res=(ArrayList<Order>) userbl.finduserorders(username);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size()&&!(res.get(i).isPending());i++)
	    {
	    	jsonArray.add(res.get(i).toJson1());
	    }
	    return jsonArray;
	}

	@GetMapping("AllOrders")
	JSONArray getAllOrders()
	{
		/* all orders: id 
		price
		*/
		ArrayList<Order> res=(ArrayList<Order>) orderbl.findallorders();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size()&&!(res.get(i).isPending());i++)
	    {
	    	jsonArray.add(res.get(i).toJson1());
	    }
	    return jsonArray;
	}
/*@GetMapping("OrderPro")
	JSONArray getOrderProducts(@RequestParam int orders_id)
	{	/*
		products:
		name, price, amount(orderd)
		orders_id
		orders_price
		*
		
	}**/

}
