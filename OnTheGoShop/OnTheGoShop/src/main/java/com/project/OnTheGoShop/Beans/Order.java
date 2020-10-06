package com.project.OnTheGoShop.Beans;

import javax.persistence.*;



@Entity(name = "Orders")
public class Order {

    int id;
//    List<order_product> products;
//    float totalPrice;
    boolean Pending;
    
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
