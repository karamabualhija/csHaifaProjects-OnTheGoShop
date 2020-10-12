package com.project.OnTheGoShop.BL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.OnTheGoShop.Beans.Driver;
import com.project.OnTheGoShop.Beans.Order;
import com.project.OnTheGoShop.Beans.Product;
import com.project.OnTheGoShop.Beans.Sort;
import com.project.OnTheGoShop.Beans.User;
import com.project.OnTheGoShop.Beans.Van;
import com.project.OnTheGoShop.Beans.order_product;
import com.project.OnTheGoShop.Beans.van_products;
import com.project.OnTheGoShop.Repo.OrderRepository;
import com.project.OnTheGoShop.Repo.Order_ProductRepository;
import com.project.OnTheGoShop.Repo.ProductRepository;
import com.project.OnTheGoShop.Repo.UserRepository;
import com.project.OnTheGoShop.Repo.van_prorepository;

@Service
public class OrderBL {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	Order_ProductRepository oderprorepo;
	@Autowired
	van_prorepository vanprorepo;
	@Autowired
	ProductRepository prorepo;
	@Autowired
	UserRepository userrepo;
	@Autowired
	DriverBL driverbl;
	
	

	public ArrayList<Order> findallorders() {
		return orderRepository.findAll();
	}

	@SuppressWarnings("unchecked")
	public JSONArray findallorderpro(int orders_id) {
	
		ArrayList<order_product> res= oderprorepo.findAllByOrderid(orders_id);
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	int productid=res.get(i).getProductid();
	    	Product pro=prorepo.findById(productid);
	    	jsonArray.add(pro.toJson1());
	    }
	    return jsonArray;
	}

	public float findprice(int orders_id) {
		ArrayList<order_product> res= oderprorepo.findAllByOrderid(orders_id);
	    float sum=0;
	    for(int i=0;i<res.size();i++)
	    {
	    	int productid=res.get(i).getProductid();
	    	Product pro=prorepo.findById(productid);
	    	int amount=res.get(i).getAmount();
	    	Float price=pro.getPrice();
	    	sum+=(amount*price);
	    }
	    return sum;

	}

	public Order fondorder(int orders_id) {
	return orderRepository.findById(orders_id);
	}

	public String placeorder(List<JSONObject> jsonstring, int userid, String lat, String lon) throws ParseException {
		
        Order order = new Order(lon,lat);
        orderRepository.save(order);
        int orderid=order.getId();
        addpros(orderid,jsonstring);
        int res=addtodriver(order);
        if(res==-1)
        {
        	orderRepository.delete(order);
			return "We Can't Place The Order";	
        }
        User user=userrepo.findById(userid);
        user.add_order(order);
        userrepo.save(user);
		return "success";

        
	}

	private int addtodriver(Order order) {
		ArrayList<Driver> drivers=driverbl.findalldrivers();
		Driver driver=null;
		double lat=Double.parseDouble(order.getLat());
		double lon=Double.parseDouble(order.getLan());
		double distance=Double.MAX_VALUE,newdistance;
	    for(int i=0;i<drivers.size();i++)
	    {
	    	Van v=drivers.get(i).getVan();
	    	boolean flag=checkstorage(v,order) ;
	    	
	    	if (flag) {
	    		newdistance=Sort.distance(Double.parseDouble(v.getLatitude()),Double.parseDouble(v.getLang()),lat, lon);
	    		if (newdistance<distance) {
	    			distance=newdistance;
	    			driver=drivers.get(i);
	    		}
	    	}
	    	
	    }
		if (driver!=null) {
			driver.getVan().addorder(order);
			updatevanstorage(driver.getVan(),order);
			driverbl.driverrepo.save(driver);
			Sort.Updatedistance(order,driver.getVan().getLatitude(),driver.getVan().getLang());
		}
		else {

			return -1;
		}
		return 1;
	}

	private void updatevanstorage(Van van, Order order) {
		ArrayList<order_product> orderproducts=oderprorepo.findAllByOrderid(order.getId());
	    for(int i=0;i<orderproducts.size();i++)
	    {
	    	van_products vp=vanprorepo.findByProductid(orderproducts.get(i).getProductid());
	    	
	    	vanprorepo.updateamount(vp.getId(), vp.getAmount()-orderproducts.get(i).getAmount());
	    }
	}

	private boolean checkstorage(Van v, Order order) {
//		ArrayList<van_products> vanproducts=vanprorepo.findAllByVanid(v.getId());
		ArrayList<order_product> orderproducts=oderprorepo.findAllByOrderid(order.getId());
	    for(int i=0;i<orderproducts.size();i++)
	    {
	    	van_products vp=vanprorepo.findByProductid(orderproducts.get(i).getProductid());
	    	if(vp==null) return false;
	    	if(vp.getAmount()<orderproducts.get(i).getAmount()) 
	    		return false;	
	    }
	    return true;
	}

	private void addpros(int orderid, List<JSONObject> jsonstring) throws ParseException {
		int amount=0,id=0;
//		JSONArray jsonarray=new JSONArray();
		
//		JSONParser jsonparser=new JSONParser(jsonstring);
//		JSONArray jsonarray=jsonparser.parseObject();
		
//		JSONParser parser = new JSONParser(jsonstring);
//		Object resultObject = parser.parse();
//		ArrayList<?> arrayList = (ArrayList<?>) resultObject;
//		jsonarray = (JSONArray) resultObject;
	    for(int i=0;i<jsonstring.size();i++)
	    {
	    	JSONObject jsonob = jsonstring.get(i);
	    	if(jsonob.containsKey("id"))
	    	 id= (int) jsonob.get("id");
	    	if(jsonob.containsKey("amount"))
	    	 amount=(int) jsonob.get("amount");
	    	order_product o_p=new order_product(orderid,id,amount);
	    	oderprorepo.save(o_p);
	    }
	}

	public void updatepen(int orders_id) {
		orderRepository.updatpending(orders_id,false);
		
		
	}
}

