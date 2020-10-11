package com.tsofen.onthegoshopClient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.R;

import java.util.ArrayList;

public class AddProductVanAdapter extends ArrayAdapter<Product> {

    LayoutInflater inflater;
    public AddProductVanAdapter(Context context, ArrayList<Product> productsArray)
    {
        super(context,0,productsArray);
        inflater=LayoutInflater.from(context);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View layout=inflater.inflate(R.layout.add_product_to_van_shape,null);
        Product pro=getItem(position);
        String[] options=new String[(int) pro.getAmount()+1];
        for (int j=0;j<options.length;j++)
            options[j]= String.valueOf(j);


        TextView proName= layout.findViewById(R.id.addProductToVanName);
        TextView proAmount = layout.findViewById(R.id.addProductToVanAmount);
        TextView proID = layout.findViewById(R.id.addProductToVanID);
        Spinner spin=layout.findViewById(R.id.amount_spinner);

        proName.setText(pro.getName());
        proAmount.setText(String.valueOf(pro.getAmount()));
        proID.setText(pro.getId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);

        return layout;

    }
}
