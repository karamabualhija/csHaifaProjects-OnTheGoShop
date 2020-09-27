package com.project.OnTheGoShop.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OnTheGoShop.BL.DriverBL;
import com.project.OnTheGoShop.BL.VanBL;
import com.project.OnTheGoShop.Beans.Driver;
import com.project.OnTheGoShop.Beans.Van;

import java.util.ArrayList;

import org.json.simple.*;

@RestController
@RequestMapping("Driver")
public class DriverController {
	@Autowired
	DriverBL driverbl;
	@Autowired
	VanBL vanbl;
	
	@GetMapping("RegisterDriver")
	String RegisterDriver(@RequestParam String name,@RequestParam String phonenumber,@RequestParam String username,@RequestParam String password,@RequestParam int vanNum)
	{
		Van van=vanbl.findvan(vanNum);
		Driver d=new Driver( van, name,  username,  password,  phonenumber);
		driverbl.add(d);
		return"";
	}
	@SuppressWarnings("unchecked")
	@GetMapping("getAllDrivers")
	JSONArray getAllDrivers()
	{/* name 
phone
vannum */
		ArrayList<Driver> res=driverbl.findalldrivers();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	jsonArray.add(res.get(i).toJson());
	    }
	    return jsonArray;
		
		
	}

}
