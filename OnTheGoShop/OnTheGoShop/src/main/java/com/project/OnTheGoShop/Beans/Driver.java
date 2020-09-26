package com.project.OnTheGoShop.Beans;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;


@Entity
public class Driver extends Person {

    Van van;
    
    public Driver(Van van,String name, String username, String password, String phonenumber) {
		super( name,  username,  password,  phonenumber);
		this.van = van;
	}

	@OneToOne
	@JoinColumn(name="VanId", referencedColumnName="id")
    public Van getVan() {
		return van;
	}

	public void setVan(Van van) {
		this.van = van;
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJson() {
		   JSONObject jo = new JSONObject();
		   jo.put("Phonenumbe", this.getPhonenumber());
		   jo.put("name", this.name);
		   jo.put("vannum", this.van.getId());

		   return jo;
	}
	@Override
	public void updatesession(HttpSession session) {
		   session.setAttribute("name", this.name);
		   session.setAttribute("username", this.username);
		   session.setAttribute("phonemumber", this.phonenumber);
		   session.setAttribute("type", "Driver");
		   session.setAttribute("vannum", this.van.getId());
		
	}

}
