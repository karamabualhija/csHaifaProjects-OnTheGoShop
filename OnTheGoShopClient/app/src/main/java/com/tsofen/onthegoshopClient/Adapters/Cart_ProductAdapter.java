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

public class Cart_ProductAdapter extends ArrayAdapter<Product> {
    LayoutInflater inflater;
    public Cart_ProductAdapter(Context context, ArrayList<Product> productsArray)
    {
        super(context,0,productsArray);
        inflater=LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View layout=inflater.inflate(R.layout.cart_product_shape,null);
        Product pro=getItem(position);
        String[] options=new String[(int) pro.getAmount()+1];
        for (int j=0;j<options.length;j++)
            options[j]= String.valueOf(j);

        ImageView proimg=layout.findViewById(R.id.productImagecart);
        TextView proname= layout.findViewById(R.id.productNameCart);
        TextView proprice = layout.findViewById(R.id.productpriceCart);
//        Spinner spin=layout.findViewById(R.id.amount_spinner);

        proimg.setImageResource(pro.getImageResId());
        proname.setText(pro.getName());
        proprice.setText(Float.toString(pro.getPrice()));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin.setAdapter(adapter);

        return layout;

    }
}
