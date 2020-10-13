package com.tsofen.onthegoshopClient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.R;

import java.util.ArrayList;

public class ProductOrderDetailAdapter extends ArrayAdapter<Product> {

    LayoutInflater inflater;

    public ProductOrderDetailAdapter(@NonNull Context context, @NonNull ArrayList<Product> objects) {
        super(context, 0, objects);
        inflater=LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = this.inflater.inflate(R.layout.order_detail_product, null);
        Product product = getItem(position);
        TextView proName = view.findViewById(R.id.productNameOrderDetails);
        TextView proPrice = view.findViewById(R.id.productPriceOrderDetails);
        TextView proAmount = view.findViewById(R.id.productAmountOrderDetails);

        proName.setText(product.getName());
        proPrice.setText(String.valueOf(product.getPrice()));
        proAmount.setText(String.valueOf(product.getAmount()));
        return view;
    }
}
