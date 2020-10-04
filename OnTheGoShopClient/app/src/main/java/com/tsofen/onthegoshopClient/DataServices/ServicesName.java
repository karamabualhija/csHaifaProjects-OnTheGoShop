package com.tsofen.onthegoshopClient.DataServices;

public enum ServicesName {
    LogIn("login"),
    Register("register"),
    AllProducts("allProducts");
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
