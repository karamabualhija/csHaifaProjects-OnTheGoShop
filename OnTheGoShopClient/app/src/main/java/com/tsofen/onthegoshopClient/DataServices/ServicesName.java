package com.tsofen.onthegoshopClient.DataServices;

public enum ServicesName {
    LogIn("login"),
    Register("register"),
    AllProducts("allProducts"),
    AllOrders("allOrders"),
    OldOrders("oldOrders"),
    OrderDetails("orderDetails");
    String url;

    ServicesName(String url){
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
