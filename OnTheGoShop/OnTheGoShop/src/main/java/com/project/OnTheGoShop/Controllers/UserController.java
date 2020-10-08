package com.project.OnTheGoShop.Controllers;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpSession;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.OnTheGoShop.Beans.Driver;
import com.project.OnTheGoShop.Beans.Person;
import com.project.OnTheGoShop.Beans.User;
import com.project.OnTheGoShop.Beans.Van;
import com.project.OnTheGoShop.BL.PersonBL;
import com.project.OnTheGoShop.BL.UserBL;
@RestController
@RequestMapping("User")
public class UserController {
	@Autowired
	PersonBL personBL;
	@Autowired
	UserBL userBL;
	
	@GetMapping("login")
	JSONObject Login(@RequestParam String username,@RequestParam String password, HttpSession session)
	{
		Person res= personBL.LogIn(username, password);
		   if(res!=null)
		   {
			   res.updatesession(session);
			   return res.toJson();	   }
		   else
			    return null;
	}
	/*@GetMapping("updatePassword")
	void updatePassword(@RequestParam int user_id,@RequestParam String Password)
	{
		
	}*/
	@SuppressWarnings("unchecked")
	@GetMapping("AllUsers")
	JSONArray getAllUsers()
	{
		List<User> res= userBL.findall();
	    JSONArray jsonArray = new JSONArray();
	    for(int i=0;i<res.size();i++)
	    {
	    	jsonArray.add(res.get(i).toJson());
	    }
	    return jsonArray;
		
	}


	@GetMapping("logout")
	void logout( HttpSession session)
	{
		Enumeration<String> keys = session.getAttributeNames();

		while( keys.hasMoreElements() ){
			
			session.removeAttribute(keys.nextElement());
		}
		
	}

	@GetMapping("Register")
	String Register(@RequestParam String name,@RequestParam String phonenumber,@RequestParam String username,@RequestParam String password) {
		User d1=userBL.findbyusername(username);
		if(d1!=null)
			return "please chane your usernaem and try again!!";
		User d=new User( name,  username,  password,  phonenumber);
		userBL.add(d);
		return "success";
	}


	
//	@GetMapping("getInfo")
//	void getInfo(HttpSession session)
//	{/*
//		name
//		phone
//		username
//		sys_id
//		*
//		
//	}

}
