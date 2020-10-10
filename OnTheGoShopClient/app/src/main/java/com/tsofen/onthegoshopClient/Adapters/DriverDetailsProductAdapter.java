package com.tsofen.onthegoshopClient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.R;

import java.util.ArrayList;

public class DriverDetailsProductAdapter extends ArrayAdapter<Product> {

    LayoutInflater inflater;
    public DriverDetailsProductAdapter(Context context, ArrayList<Product> productsArray) {
        super(context, 0 , productsArray);
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View layout = this.inflater.inflate(R.layout.driver_details_product_shape, null);
        Product pro = getItem(position);
//        ImageView proimg=layout.findViewById(R.id.productImage);
        TextView proname= layout.findViewById(R.id.productNameDriverDetails);
        TextView proAmount= layout.findViewById(R.id.productAmountDriverDetails);
        TextView proprice = layout.findViewById(R.id.productPriceDriverDetails);
//        proimg.setImageResource(pro.getImageResId());
        proname.setText(pro.getName());
        proAmount.setText(String.valueOf(pro.getAmount()));
        proprice.setText(Float.toString(pro.getPrice()));
        return layout;

    }
}
