package com.project.OnTheGoShop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OnTheGoShop.BL.OrderBL;
import com.project.OnTheGoShop.BL.UserBL;
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
	
	@SuppressWarnings("unchecked")
	@GetMapping("ActiveOrders")
	JSONArray getActiveOrders(HttpSession session)
	{
		int sys_id=(int) session.getAttribute("id");
		ArrayList<Order> res=(ArrayList<Order>) userbl.finduserorders(sys_id);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size()&&res.get(i).isPending();i++)
	    {
	    		int id=res.get(i).getId();
			   JSONObject jo = new JSONObject();
			   jo.put("id", id);
			   jo.put("price",orderbl.findprice(id) );
			   jsonArray.add(jo);
	    }
	    return jsonArray;
		
	}
	@SuppressWarnings("unchecked")
	@GetMapping("OldOrders")
	JSONArray getOldOrders(HttpSession session)
	{
		int sys_id=(int) session.getAttribute("id");
		ArrayList<Order> res=(ArrayList<Order>) userbl.finduserorders(sys_id);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size()&&!(res.get(i).isPending());i++)
	    {
	    	int id=res.get(i).getId();
		   JSONObject jo = new JSONObject();
		   jo.put("id", id);
		   jo.put("price",orderbl.findprice(id) );
		   jsonArray.add(jo);
	    }
	    return jsonArray;
	}
	@SuppressWarnings("unchecked")
	@GetMapping("getUserOrders")
	JSONArray getUserOrders(@RequestParam String username)
	{
		ArrayList<Order> res=(ArrayList<Order>) userbl.finduserorders(username);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size()&&!(res.get(i).isPending());i++)
	    {
    		int id=res.get(i).getId();
		   JSONObject jo = new JSONObject();
		   jo.put("id", id);
		   jo.put("price",orderbl.findprice(id) );
		   jsonArray.add(jo);
	    }
	    return jsonArray;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("AllOrders")
	JSONArray getAllOrders()
	{
		ArrayList<Order> res=(ArrayList<Order>) orderbl.findallorders();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size()&&!(res.get(i).isPending());i++)
	    {
    		int id=res.get(i).getId();
		   JSONObject jo = new JSONObject();
		   jo.put("id", id);
		   jo.put("price",orderbl.findprice(id) );
		   jsonArray.add(jo);;
	    }
	    return jsonArray;
	}
   @GetMapping("OrderPro")
	JSONArray getOrderProducts(@RequestParam int orders_id)
	{	

	   return orderbl.findallorderpro(orders_id);

	}
   @GetMapping("Orderprice")
	double getOrderPice(@RequestParam int orders_id)
	{	

	   return orderbl.findprice(orders_id);

	}

}
