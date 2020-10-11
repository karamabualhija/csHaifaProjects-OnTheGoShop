package com.tsofen.onthegoshopClient.DataServices;

public enum ServicesName {
    LogIn("User/login"),
    Register("User/Register"),
    LogOut("User/logout"),
    AllUsers("User/AllUsers"),
    AllProducts("Product/AllProducts"),
    AddProduct("Product/AddNewProduct"),
    ManagerAllProduct("Product/AllProductsManger"),
    AllDrivers("Driver/getAllDrivers"),
    AddDriver("Driver/RegisterDriver"),
    DriverLocation("Van/getVanLocatoin"),
    DriverProduct("Van/getVanStorage"),
    DriverOrders("Van/getVanOrders"),
    UpdateDriverLocation("Van/updatelocation"),
    AddProductVan(""),
    SetOrderDel(""),
    AllOrders("Order/AllOrders"),
    OldOrders("Order/OldOrders"),
    ActiveOrder("Order/ActiveOrders"),
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
