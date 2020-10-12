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
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.json.ParseException;
import org.json.simple.*;

@RestController
@RequestMapping("Order")
public class OrderController {
	@Autowired
	OrderBL orderbl;
	@Autowired
	UserBL userbl;
	
	@GetMapping("placeOrder")
	String place(@RequestParam List<JSONObject> jsonstrinf, @RequestParam int user_id, @RequestParam String lat, @RequestParam String lon) throws ParseException
	{
		return orderbl.placeorder(jsonstrinf,user_id,lat,lon);
		
		
	}
	
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
    		String lan=res.get(i).getLan();
    		String lat=res.get(i).getLat();    		
		   JSONObject jo = new JSONObject();
		   jo.put("id", id);
		   jo.put("lan", lan);
		   jo.put("lat", lat);
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
    		String lan=res.get(i).getLan();
    		String lat=res.get(i).getLat();    		
		   JSONObject jo = new JSONObject();
		   jo.put("id", id);
		   jo.put("lan", lan);
		   jo.put("lat", lat);
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
    		String lan=res.get(i).getLan();
    		String lat=res.get(i).getLat();    		
		   JSONObject jo = new JSONObject();
		   jo.put("id", id);
		   jo.put("lan", lan);
		   jo.put("lat", lat);
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
    		String lan=res.get(i).getLan();
    		String lat=res.get(i).getLat();    		
		   JSONObject jo = new JSONObject();
		   jo.put("id", id);
		   jo.put("lan", lan);
		   jo.put("lat", lat);
		   jo.put("price",orderbl.findprice(id) );
		   jsonArray.add(jo);
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
   @SuppressWarnings("unchecked")
   @GetMapping("OrderLocation")
	JSONObject getlocation(@RequestParam int orders_id)
	{	

		Order res=orderbl.fondorder(orders_id);
		String lan=res.getLan();
		String lat=res.getLat();    		
	   JSONObject jo = new JSONObject();
	   jo.put("lan", lan);
	   jo.put("lat", lat);
	   return jo;

	}

   

}
