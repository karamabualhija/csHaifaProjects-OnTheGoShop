package com.tsofen.onthegoshopClient.ManagerViews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tsofen.onthegoshopClient.Adapters.OrderAdapter;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.DataHandlers.AllOrdersHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.AllOrdersThread;
import com.tsofen.onthegoshopClient.UserViews.OrderDetails;

import java.util.ArrayList;

public class ManagerOrders extends AppCompatActivity {

    private static final String TAG = "ManagerOrders";

    private ListView managerOrderList;
    private ArrayList<Order> orders;
    private OrderAdapter orderAdapter;

    HandlerThread managerOrderHandlerThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_orders);

        managerOrderList = findViewById(R.id.managerOrderList);

        managerOrderHandlerThread = new HandlerThread("managerOrderHandlerThread");
        managerOrderHandlerThread.start();

        AllOrdersThread allOrdersThread = new AllOrdersThread(new AllOrdersHandler() {
            @Override
            public void onOrdersReceived(final ArrayList<Order> orders) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setOrderList(orders);
                    }
                });
            }

            @Override
            public void onFailure() {
                Toast.makeText(ManagerOrders.this, "failed to receive the data make sure you have internet connection", Toast.LENGTH_LONG).show();
            }
        });

        Handler handler = new Handler(managerOrderHandlerThread.getLooper());
        handler.post(allOrdersThread);
    }

    private void setOrderList(ArrayList<Order> orderArrayList) {
        orders = orderArrayList;
        orderAdapter = new OrderAdapter(this,orders);
        managerOrderList.setAdapter(orderAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (managerOrderHandlerThread!=null && managerOrderHandlerThread.isAlive())
            managerOrderHandlerThread.quit();
    }

    public void orderDetails(View view){
        LinearLayout linearLayout = (LinearLayout) view.getParent();
        TextView orderIdTv = linearLayout.findViewById(R.id.orderid);
        TextView orderPriceTv = linearLayout.findViewById(R.id.orderprice);

        String orderID= orderIdTv.getText().toString();
        String orderPrice= orderPriceTv.getText().toString();
        Intent intent = new Intent(this, OrderDetails.class);
        intent.putExtra("orderID", orderID);
        intent.putExtra("orderPrice", orderPrice);
        startActivity(intent);
    }
}
