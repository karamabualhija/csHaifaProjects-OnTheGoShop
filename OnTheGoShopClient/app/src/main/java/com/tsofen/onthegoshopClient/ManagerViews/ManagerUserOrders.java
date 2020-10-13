package com.tsofen.onthegoshopClient.ManagerViews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.ListView;

import com.tsofen.onthegoshopClient.Adapters.DriverDetailsOrderAdapter;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.DataHandlers.UserOrderHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.UserOrderThread;

import java.util.ArrayList;

public class ManagerUserOrders extends AppCompatActivity {

    private ListView userOrdersListV;
    private ArrayList<Order> userOrders;
    private DriverDetailsOrderAdapter adapter;
    private HandlerThread usersOrdersHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user_orders);

        userOrdersListV = findViewById(R.id.usersOrderList);
        String username = getIntent().getStringExtra("username");

        usersOrdersHandlerThread = new HandlerThread("userOrderHandlerThread");
        usersOrdersHandlerThread.start();

        UserOrderThread userOrderThread = new UserOrderThread(UserOrderThread.All_ORDERS, username, new UserOrderHandler() {
            @Override
            public void onOrdersReceived(ArrayList<Order> orders) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        userOrders = orders;
                        adapter = new DriverDetailsOrderAdapter(ManagerUserOrders.this, userOrders);
                        userOrdersListV.setAdapter(adapter);
                    }
                });
            }
        });

        Handler handler = new Handler(usersOrdersHandlerThread.getLooper());
        handler.post(userOrderThread);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (usersOrdersHandlerThread!=null && usersOrdersHandlerThread.isAlive())
            usersOrdersHandlerThread.quit();
    }
}