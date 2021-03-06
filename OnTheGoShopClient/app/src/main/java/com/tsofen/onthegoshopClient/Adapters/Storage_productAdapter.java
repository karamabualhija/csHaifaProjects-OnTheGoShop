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

public class Storage_productAdapter extends ArrayAdapter<Product> {
    LayoutInflater inflater;
    public Storage_productAdapter(Context context, ArrayList<Product> productsArray)
    {
        super(context,0,productsArray);
        inflater=LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View layout=inflater.inflate(R.layout.storage_product_shape,null);
        Product pro=getItem(position);
        TextView proname= layout.findViewById(R.id.productNamestorage);
        TextView proprice = layout.findViewById(R.id.productpricestorage);
        TextView proid = layout.findViewById(R.id.productidstorage);
        TextView proamount = layout.findViewById(R.id.productamountstorage);

        proname.setText(pro.getName());
        proprice.setText(Float.toString(pro.getPrice()));
        proamount.setText(Double.toString(pro.getAmount()));
        proid.setText(String.valueOf(pro.getId()));
        return layout;

    }
}
