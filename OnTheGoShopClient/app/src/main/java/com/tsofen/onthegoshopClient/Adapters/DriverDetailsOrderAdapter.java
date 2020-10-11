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

public class DriverDetailsOrderAdapter extends ArrayAdapter<Order> {

    LayoutInflater inflater;
    public DriverDetailsOrderAdapter(Context context, ArrayList<Order> orderArrayList) {
        super(context, 0 , orderArrayList);
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View layout = this.inflater.inflate(R.layout.driver_details_order_shape, null);
        Order order = getItem(position);
        TextView orderId= layout.findViewById(R.id.orderIdDriverDetails);
        TextView orderPrice= layout.findViewById(R.id.orderPriceDriverDetails);
        TextView orderLatLon = layout.findViewById(R.id.orderLatLonDriverDetails);
        orderId.setText(String.valueOf(order.getId()));
        orderPrice.setText(String.valueOf(order.getTotalPrice()));
        orderLatLon.setText(order.getLatLng().toString());
        return layout;

    }
}
