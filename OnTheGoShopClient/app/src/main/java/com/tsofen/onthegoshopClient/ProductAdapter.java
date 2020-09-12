package com.tsofen.onthegoshopClient;

import android.content.Context;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    LayoutInflater inflater;
    public ProductAdapter(Context context, ArrayList<Product> productsArray) {
        super(context, 0 , productsArray);
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View layout = this.inflater.inflate(R.layout.product_shape, null);
        Product pro = getItem(position);
        ImageView proimg=layout.findViewById(R.id.productImage);
        TextView proname= layout.findViewById(R.id.productName);
        TextView proprice = layout.findViewById(R.id.productprice);
        proimg.setImageResource(pro.getImageResId());
        proname.setText(pro.getName());
        proprice.setText(Float.toString(pro.getPrice()));
        return layout;

    }


}
