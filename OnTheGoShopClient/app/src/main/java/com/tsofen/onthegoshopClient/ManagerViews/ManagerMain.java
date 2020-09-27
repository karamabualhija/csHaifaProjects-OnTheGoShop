package com.tsofen.onthegoshopClient.ManagerViews;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tsofen.onthegoshopClient.R;

public class ManagerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);
    }

    public void setStorage(View view) {
        Intent intent = new Intent(this, ManagerStorage.class);
        startActivity(intent);
    }
    public void setDrivers(View view) {
        Intent intent = new Intent(this, ManagerStorage.class);
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
}