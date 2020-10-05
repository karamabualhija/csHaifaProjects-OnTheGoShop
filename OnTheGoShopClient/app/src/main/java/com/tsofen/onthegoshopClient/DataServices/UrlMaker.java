package com.tsofen.onthegoshopClient.DataServices;

import java.util.Map;

public class UrlMaker {
    private static final String TAG = "UrlMaker";
    private static final String baseUrl = "https://localhost:8080/";
    public String createUrl(ServicesName service, Map<String, String> params){
        if (params == null)
            return baseUrl + service.getUrl();
        StringBuilder serviceUrl = new StringBuilder(baseUrl + service.getUrl() + "?");
        for (String param:params.keySet()){
            serviceUrl.append(String.format("%s=%s&", param, params.get(param)));
        }
        return serviceUrl.toString();
    }
}
