package com.tsofen.onthegoshopClient.UserViews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.tsofen.onthegoshopClient.Adapters.OrderAdapter;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.DataHandlers.UserOrderHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.UserOrderThread;

import java.util.ArrayList;

public class UserOrderFragment extends Fragment {

    private static final String TAG = "UserOrderFragment";

    private ListView userOrderList;
    private LinearLayout allOrders;
    private LinearLayout oldOrders;
    private ArrayList<Order> ordersToShow;
    private OrderAdapter orderAdapter = null;
    private HandlerThread userOrderHandlerThread;
    private Handler handler;
    private UserOrderThread orderThread;
    int userID;

    public UserOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_order, container, false);
        userOrderList = view.findViewById(R.id.UserOrderList);
        allOrders = view.findViewById(R.id.allOrderLin);
        oldOrders = view.findViewById(R.id.oldOrderLin);
        allOrders.setPressed(true);

        userID = Integer.parseInt(getActivity().getSharedPreferences("login", Context.MODE_PRIVATE).getString("id",null));

        ordersToShow = new ArrayList<>();
        orderAdapter = new OrderAdapter(getContext(), ordersToShow);
        userOrderList.setAdapter(orderAdapter);

        userOrderHandlerThread = new HandlerThread("userOrderHandlerThread");
        userOrderHandlerThread.start();

//        orderThread = new UserOrderThread(UserOrderThread.WAITING_ORDERS, new UserOrderHandler() {
//            @Override
//            public void onOrdersReceived(final ArrayList<Order> orders) {
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ordersToShow = orders;
//                        orderAdapter = new OrderAdapter(getContext(), ordersToShow);
//                        userOrderList.setAdapter(orderAdapter);
//                    }
//                });
//            }
//        });
//        handler = new Handler(userOrderHandlerThread.getLooper());
//        handler.post(orderThread);

        allOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allOrders.setPressed(true);
                oldOrders.setPressed(false);
                orderThread = new UserOrderThread(UserOrderThread.WAITING_ORDERS, userID, new UserOrderHandler() {
                    @Override
                    public void onOrdersReceived(final ArrayList<Order> orders) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ordersToShow.clear();
                                ordersToShow.addAll(orders);
                                orderAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                handler = new Handler(userOrderHandlerThread.getLooper());
                handler.post(orderThread);
            }
        });

        oldOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allOrders.setPressed(false);
                oldOrders.setPressed(true);
                orderThread = new UserOrderThread(UserOrderThread.OLD_ORDERS, userID, new UserOrderHandler() {
                    @Override
                    public void onOrdersReceived(final ArrayList<Order> orders) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ordersToShow.clear();
                                ordersToShow.addAll(orders);
                                orderAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                handler = new Handler(userOrderHandlerThread.getLooper());
                handler.post(orderThread);
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (userOrderHandlerThread!=null && userOrderHandlerThread.isAlive())
            userOrderHandlerThread.quit();
    }
}