package com.project.OnTheGoShop.Beans;

import java.util.ArrayList;

public abstract class Sort {
//	ArrayList<Order> orders;
//	String lang;
//	String lat;
//	public ArrayList<Order> getOrders() {
//		return orders;
//	}
//	public void setOrders(ArrayList<Order> orders) {
//		this.orders = orders;
//	}
//	public String getLang() {
//		return lang;
//	}
//	public void setLang(String lang) {
//		this.lang = lang;
//	}
//	public String getLat() {
//		return lat;
//	}
//	public void setLat(String lat) {
//		this.lat = lat;
//	}
//	public sort(ArrayList<Order> orders, String lat,String lang) {
//		super();
//		this.orders = orders;
//		this.lang = lang;
//		this.lat = lat;
//	}
//	public sort() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
	public static void Updatedistances(ArrayList<Order> orders,String lang,String lat) {
		    for(int i=0;i<orders.size();i++)
		    {
		    	double d=distance(Double.parseDouble(lat),Double.parseDouble(lang), Double.parseDouble(orders.get(i).getLat()),Double.parseDouble(orders.get(i).getLan() ));
		    	orders.get(i).setDistance(d);
		    }
		
	}
	public static void Updatedistance(Order order,String lang,String lat) {

	    	double d=distance(Double.parseDouble(lat),Double.parseDouble(lang), Double.parseDouble(order.getLat()),Double.parseDouble(order.getLan() ));
	    	order.setDistance(d);
	    
	
	}
	

	
	
    public static double distance(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;

        return (dist);
	      }
    public static double deg2rad(double deg) {
	        return (deg * Math.PI / 180.0);
	      }
	  public static double rad2deg(double rad) {
	        return (rad * 180.0 / Math.PI);
	      }


}
