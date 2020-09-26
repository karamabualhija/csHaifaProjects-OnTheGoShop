package com.project.OnTheGoShop.Beans;

import java.security.MessageDigest;

import javax.persistence.*;
import javax.servlet.http.HttpSession;

@Entity(name = "users")
@Table(uniqueConstraints =  @UniqueConstraint(columnNames = { "username" }))
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Person {


    String name;
    int sys_id;
    String username;
    String password;
    String phonenumber;

    public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Person(String name, String username, String password, String phonenumber) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.phonenumber = phonenumber;
	}

	@Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Id
    @GeneratedValue
    public int getSys_id() {
        return sys_id;
    }

    public void setSys_id(int sys_id) {
        this.sys_id = sys_id;
    }

    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
    
	public void updatesession(HttpSession session) {
		
	}
	public boolean validate(String pass)
	{

		return (hashPassword(pass).equals(this.password));
	}
	protected String hashPassword(String base)
	{
	    try{
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");
	        byte[] hash = digest.digest(base.getBytes("UTF-8"));
	        StringBuffer hexString = new StringBuffer();

	        for (int i = 0; i < hash.length; i++) {
	            String hex = Integer.toHexString(0xff & hash[i]);
	            if(hex.length() == 1) hexString.append('0');
	            hexString.append(hex);
	        }
	        return hexString.toString();
	    } catch(Exception ex){
	       throw new RuntimeException(ex);
	    }
	}



}
