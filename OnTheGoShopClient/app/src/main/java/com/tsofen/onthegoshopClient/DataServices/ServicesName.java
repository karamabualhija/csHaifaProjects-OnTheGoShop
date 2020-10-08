package com.tsofen.onthegoshopClient.DataServices;

public enum ServicesName {
    LogIn("User/login"),
    Register("User/Register"),
    LogOut("User/logout"),
    AllUsers("User/AllUsers"),
    AllProducts("Product/AllProducts"),
    ManagerAllProduct("Product/AllProductManger"),
    AllDrivers("Driver/getAllDrivers"),
    AllOrders("Order/AllOrders"),
    OldOrders("Order/OldOrders"),
    OrderDetails("Order/OrderDetails");


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
