package com.tsofen.onthegoshopClient.UserViews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.ListView;
import android.widget.TextView;

import com.tsofen.onthegoshopClient.Adapters.ProductOrderDetailAdapter;
import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DataHandlers.OrderDetailsHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.OrderDetailsThread;

import java.util.ArrayList;

public class OrderDetails extends AppCompatActivity {

    ListView productsList;
    TextView orderId;
    TextView orderPrice;
    ArrayList<Product> listProducts;
    ProductOrderDetailAdapter listAdapter;
    HandlerThread orderDetailsHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        String title = "Order " + getIntent().getIntExtra("orderID", 0) + "contains:";
        String totalPrice = String.valueOf(getIntent().getFloatExtra("orderPrice", 0));

        productsList = findViewById(R.id.orderDetailsList);
        orderId = findViewById(R.id.orderDetailsID);
        orderPrice = findViewById(R.id.orderDetailsPrice);

        orderId.setText(title);
        orderPrice.setText(totalPrice);

        OrderDetailsThread orderDetailsThread = new OrderDetailsThread(new OrderDetailsHandler() {
            @Override
            public void onProductsReceived(ArrayList<Product> products) {
                listProducts = products;
                listAdapter = new ProductOrderDetailAdapter(getBaseContext(), listProducts);
                productsList.setAdapter(listAdapter);
            }
        }, getIntent().getIntExtra("orderID", 0));
        orderDetailsHandlerThread = new HandlerThread("orderDetailsHandlerThread");
        orderDetailsHandlerThread.start();
        Handler handler = new Handler(orderDetailsHandlerThread.getLooper());
        handler.post(orderDetailsThread);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orderDetailsHandlerThread!=null&&orderDetailsHandlerThread.isAlive()){
            orderDetailsHandlerThread.quit();
        }
    }
}