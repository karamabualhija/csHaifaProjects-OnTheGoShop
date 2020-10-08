package com.tsofen.onthegoshopClient.ThreadServices;

import android.os.SystemClock;
import android.util.Log;

import com.tsofen.onthegoshopClient.Beans.User;
import com.tsofen.onthegoshopClient.DataHandlers.LogInHandler;
import com.tsofen.onthegoshopClient.DataServices.OnDataReadyHandler;
import com.tsofen.onthegoshopClient.DataServices.ServicesName;
import com.tsofen.onthegoshopClient.DataServices.TextDownloader;
import com.tsofen.onthegoshopClient.DataServices.UrlMaker;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogInThread implements Runnable{
    private static final String TAG = "LogInThread";
    private String username;
    private String password;
    private LogInHandler logInHandler;

    public LogInThread(String username, String password, LogInHandler logInHandler) {
        this.username = username;
        this.password = password;
        this.logInHandler = logInHandler;
    }

    @Override
    public void run() {
        UrlMaker urlMaker = new UrlMaker();
        Map<String, String> params = new HashMap<>();
        params.put("username", this.username);
        params.put("password", this.password);

        Log.d(TAG, "run: username: " + this.username+ " password: " + this.password);

        TextDownloader textDownloader = TextDownloader.getInstance();
        textDownloader.getText(urlMaker.createUrl(ServicesName.LogIn, params), new OnDataReadyHandler() {
            @Override
            public void onDataDownloadCompleted(String downloadedData) {
                try {
                    JSONObject jsonObject = new JSONObject(downloadedData);
                    if (jsonObject.has("type")){
                        if (jsonObject.getString("type").equals("User")){
                            User user = new User();
                            user.setPhonenumber(jsonObject.getString("phonenumber"));
                            user.setUsername(jsonObject.getString("username"));
                            user.setName(jsonObject.getString("name"));
                            logInHandler.OnUserLogIn(user);
                        }
                        else if(jsonObject.getString("type").equals("Manager")){
                            logInHandler.OnManagerLogIn();

                        }else if(jsonObject.getString("type").equals("Driver")){
                            logInHandler.OnDriverLogIn();
                        }
                        else{
                            logInHandler.OnLogInFailure();
                        }
                    } else {
                        User user = new User();
                        user.setPhonenumber("0546074508");
                        user.setUsername("karam");
                        user.setName("karam abu alhija");
                        logInHandler.OnUserLogIn(user);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDownloadError() {

            }
        });
    }
}
