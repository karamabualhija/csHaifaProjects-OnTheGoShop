package com.tsofen.onthegoshopClient.UserViews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.tsofen.onthegoshopClient.Adapters.Cart_ProductAdapter;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DBHandler.CartDBHandler;
import com.tsofen.onthegoshopClient.R;

import java.util.ArrayList;


public class UserCartFragment extends Fragment {

    ListView userCartList;
    Button button;
    CartDBHandler dbHandler;
    Button delButton;
    ArrayList<Product> products;
    Cart_ProductAdapter cartAdapter;

    public UserCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_cart, container, false);
        button  = view.findViewById(R.id.placeOrderb);
        delButton = view.findViewById(R.id.DeleteCartBtn);

        dbHandler = new CartDBHandler(getContext(), null, 1);
        products = (ArrayList<Product>) dbHandler.getProducts();
        cartAdapter = new Cart_ProductAdapter(getContext(), products);
        userCartList= (ListView) view.findViewById(R.id.UserCartList);
        userCartList.setAdapter(cartAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products = (ArrayList<Product>) dbHandler.getProducts();
                Order order = new Order();
                order.setProducts(products);
                Intent intent = new Intent(getContext(), OrderMapActivity.class);
                intent.putExtra("newOrder", order);
                startActivity(intent);
                //TODO send to the server the order data
                dbHandler.deleteProducts();
                //TODO set the cart num to 0
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getId();
                String productName = ((Product)userCartList.getItemAtPosition(position)).getName();
                products.remove(position);
                cartAdapter.notifyDataSetChanged();
                dbHandler.deleteProduct(productName);
                //TODO update the num on cart
            }
        });
        return view;
    }
}