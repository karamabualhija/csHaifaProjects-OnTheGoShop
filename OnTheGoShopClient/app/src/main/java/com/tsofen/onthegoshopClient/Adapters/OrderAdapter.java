package com.tsofen.onthegoshopClient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.R;

import java.util.ArrayList;

public class OrderAdapter extends ArrayAdapter<Order> {
    LayoutInflater inflater;


    public OrderAdapter( Context context,   ArrayList<Order> OrdersArray) {
        super(context, 0,  OrdersArray);
        inflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View layout=this.inflater.inflate(R.layout.order_shape,null);
        Order order = getItem(position);
        TextView orderid= layout.findViewById(R.id.orderid);
        TextView orderprice = layout.findViewById(R.id.orderprice);
        orderid.setText(String.valueOf(order.getId()));
        orderprice.setText(Float.toString(order.getTotalPrice()));
        return layout;

    }
}
