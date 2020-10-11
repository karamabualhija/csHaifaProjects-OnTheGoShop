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

public class DriverOrderNavAdapter extends ArrayAdapter<Order> {

    LayoutInflater inflater;


    public DriverOrderNavAdapter(Context context, ArrayList<Order> OrdersArray) {
        super(context, 0,  OrdersArray);
        inflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View layout=this.inflater.inflate(R.layout.driver_orders_nav_shape,null);
        Order order = getItem(position);
        TextView orderId = layout.findViewById(R.id.orderIdDriverNav);
        TextView orderLat = layout.findViewById(R.id.orderLatDriverNav);
        TextView orderLon = layout.findViewById(R.id.orderLonDriverNav);
        String id = String.valueOf(order.getId());
        String lat = String.valueOf(order.getLatLng().latitude);
        String lon = String.valueOf(order.getLatLng().longitude);
        orderId.setText(id);
        orderLat.setText(lat);
        orderLon.setText(lon);
        return layout;

    }
}
