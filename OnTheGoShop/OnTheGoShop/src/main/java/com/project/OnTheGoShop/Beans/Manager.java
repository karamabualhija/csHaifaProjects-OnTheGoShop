package com.project.OnTheGoShop.Beans;

import javax.persistence.Entity;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

@Entity
public class Manager extends Person {

	public Manager() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public JSONObject toJson()
	{
		   JSONObject jo = new JSONObject();
		   jo.put("name", this.name);
		   jo.put("username", this.username);
		   jo.put("phone", this.phonenumber);
		   jo.put("type", "Manager");
		   jo.put("id", this.id);
		   return jo;
	}
	@Override
	public void updatesession(HttpSession session) {
		   session.setAttribute("name", this.name);
		   session.setAttribute("username", this.username);
		   session.setAttribute("phonemumber", this.phonenumber);
		   session.setAttribute("type", "Manager");
		
	}


	

}
