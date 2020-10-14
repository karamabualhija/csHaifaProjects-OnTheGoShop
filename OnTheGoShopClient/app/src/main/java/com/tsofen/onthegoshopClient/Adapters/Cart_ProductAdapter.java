package com.tsofen.onthegoshopClient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

        TextView proname= layout.findViewById(R.id.productNameCart);
        TextView proprice = layout.findViewById(R.id.productpriceCart);
        TextView proid = layout.findViewById(R.id.productIdCart);
        EditText spin=layout.findViewById(R.id.amount_spinner);

        proname.setText(pro.getName());
        proprice.setText(Float.toString(pro.getPrice()));
        proid.setText(String.valueOf(pro.getId()));

        return layout;

    }
}

