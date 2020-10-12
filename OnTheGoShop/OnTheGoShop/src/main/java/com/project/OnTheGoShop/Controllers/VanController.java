package com.project.OnTheGoShop.Controllers;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OnTheGoShop.BL.OrderBL;
import com.project.OnTheGoShop.BL.ProductBL;
import com.project.OnTheGoShop.BL.VanBL;
import com.project.OnTheGoShop.Beans.Order;
import com.project.OnTheGoShop.Beans.Product;
import com.project.OnTheGoShop.Beans.Sort;
import com.project.OnTheGoShop.Beans.Van;
import com.project.OnTheGoShop.Beans.order_product;
import com.project.OnTheGoShop.Beans.van_products;
import com.project.OnTheGoShop.Repo.VanRepository;
import com.project.OnTheGoShop.Repo.van_prorepository;
@RestController
@RequestMapping("Van")
public class VanController {
	@Autowired
	VanBL vanbl;

	@Autowired
	OrderBL orderbl;
	@Autowired
	ProductBL probl;
	@Autowired
	van_prorepository vanpr;

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
//		Van res=vanbl.findvan(id);
		ArrayList<van_products> products=(ArrayList<van_products>) vanpr.findAllByVanid(id);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<products.size();i++)
	    {
	    	int productid=products.get(i).getProductid();
	    	Product pro=probl.findpro(productid);
	    	pro.setAmount(products.get(i).getAmount());
	    	jsonArray.add(pro.toJson1());
	    }
	    return jsonArray;


		
	}

	@SuppressWarnings("unchecked")
	@GetMapping("getVanOrders")
	JSONArray getVanOrdere(HttpSession session,@RequestParam int id)
	{
		/*
		orders_id
		orders_price
		*/
//		int id=(int) session.getAttribute("id");
		Van res=vanbl.findvan(id);
		List<Order> orders= res.getOrders();
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
	  Van v=vanbl.findvan(id);
	  Sort.Updatedistances(v.getOrders(),lag,lan);
	 		
	}

	@GetMapping("addtostorage")
	String addtostorage(@RequestParam int van_id,@RequestParam int pro_id,@RequestParam int amount) {
		Product pro=probl.findpro(pro_id);
		int proamount=pro.getAmount();
		if(proamount<amount)
			return "there not enough storage";
		probl.updatestorage(pro_id,proamount-amount);
		van_products vp=vanpr.findByProductid(pro_id);
		if(vp==null) {
			System.out.println("van id: " + van_id);
		vp= new van_products(van_id,pro_id,amount);
		vanpr.save(vp);}
		else {
			vanpr.updateamount(vp.getId(),vp.getAmount()+amount);
		}

		probl.updatestorage(pro_id, proamount-amount);
		return "successss";
	}

	

}
