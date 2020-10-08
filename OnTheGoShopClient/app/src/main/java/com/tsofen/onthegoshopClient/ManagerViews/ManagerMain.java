package com.tsofen.onthegoshopClient.ManagerViews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tsofen.onthegoshopClient.LogIn.MainActivity;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.LogoutThread;

public class ManagerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
    }

    public void setStorage(View view) {
        Intent intent = new Intent(this, ManagerStorage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void setDrivers(View view) {
        Intent intent = new Intent(this, ManagerStorage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void setVans(View view) {
        Intent intent = new Intent(this, ManagerStorage.class);
        startActivity(intent);
    }
    public void setUsers(View view) {
        Intent intent = new Intent(this, ManagerStorage.class);
        startActivity(intent);
    }
    public void setOrders(View view) {
        Intent intent = new Intent(this, ManagerStorage.class);
        startActivity(intent);
    }

    public void mangerLogout(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor edit  = sharedPreferences.edit();
        edit.remove("loggedIn");
        edit.apply();

        HandlerThread logoutHandlerThread = new HandlerThread("logoutHandlerThread");
        logoutHandlerThread.start();
        Handler handler =  new Handler(logoutHandlerThread.getLooper());
        handler.post(new LogoutThread());
        
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}