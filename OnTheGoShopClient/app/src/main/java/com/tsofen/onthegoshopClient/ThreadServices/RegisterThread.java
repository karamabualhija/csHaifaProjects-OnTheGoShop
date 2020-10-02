package com.tsofen.onthegoshopClient.ThreadServices;

import com.tsofen.onthegoshopClient.Beans.User;
import com.tsofen.onthegoshopClient.DataHandlers.RegisterHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import java.util.HashMap;
import java.util.Map;

public class RegisterThread implements Runnable {
    private static final String TAG = "RegisterThread";
    private User user;
    private RegisterHandler registerHandler;

    public RegisterThread(User user, RegisterHandler registerHandler) {
        this.user = user;
        this.registerHandler = registerHandler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        Map<String, String> params = new HashMap<>();
        params.put("name", user.getName());
        params.put("username", user.getUsername());
        params.put("password", user.getPassword());
        params.put("phonenumber", user.getPhonenumber());

        TextDownloader textDownloader = TextDownloader.getInstance();

        textDownloader.getText(urlMaker.createUrl(ServicesName.Register, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                registerHandler.onRegisterSuccess();
            }

            @Override
            public void onDownloadError() {

            }
        });
    }
}
