package com.project.OnTheGoShop.Beans;

import javax.persistence.*;



@Entity(name = "Orders")
public class  Order implements Comparable<Order> {

    int id;
    String lan;
    String lat;
//    List<order_product> products;
//    float totalPrice;
    boolean Pending;
   double distance;
   
    
    
    public double getDistance() {
	return distance;
}



public void setDistance(double distance) {
	this.distance = distance;
}



	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Order(int id, String lan, String lat, boolean pending) {
		super();
		this.id = id;
		this.lan = lan;
		this.lat = lat;
		Pending = pending;
	}

	public Order( String lan, String lat) {
		super();
		this.lan = lan;
		this.lat = lat;
		Pending = true;
	}

	@Column
    public boolean isPending() {
		return Pending;
	}

	public void setPending(boolean pending) {
		Pending = pending;
	}

	@Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column
    public String getLan() {
		return lan;
	}


	public void setLan(String lan) {
		this.lan = lan;
	}

	@Column
	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}



	@Override
	public int compareTo(Order other) {
	       if(this.getDistance() > other.getDistance())
	            return 1;
	        else if (this.getDistance() == other.getDistance())
	            return 0 ;
	        return -1 ;
	}




//    @ManyToMany
//    public List<order_product> getProducts() {
//        return products;
//    }
//
//    public void setProducts(List<order_product> products) {
//        this.products = products;
//    }

//    @Column
//    public float getTotalPrice() {
//        return totalPrice;
//    }
//
//    public void setTotalPrice(float totalPrice) {
//        this.totalPrice = totalPrice;
//    }

//	@SuppressWarnings("unchecked")
//	public JSONObject toJson1() {
//		   JSONObject jo = new JSONObject();
//		   jo.put("id", this.id);
////		   jo.put("price", this.totalPrice);
//		   return jo;
//	}
//	private void  updatetotalprice()
//	{
//		int sum=0;
//	    for(int i=0;i<products.size();i++)
//	    {
//	    	
//	    	sum+=(products.get(i).ge);
//	    }
//	    this.totalPrice=sum;
//
//	}

}
