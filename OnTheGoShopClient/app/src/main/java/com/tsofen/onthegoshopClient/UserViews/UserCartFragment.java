package com.tsofen.onthegoshopClient.UserViews;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    private static final String TAG = "UserCartFragment";

    ListView userCartList;
    Button button;
    CartDBHandler dbHandler;
    ArrayList<Product> products;
    Cart_ProductAdapter cartAdapter;
    CartListAdapters cartListAdapters;

    public UserCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_cart, container, false);
        button  = view.findViewById(R.id.placeOrderb);

        cartListAdapters = CartListAdapters.getInstance();

        dbHandler = new CartDBHandler(getContext(), null, 1);
        products = (ArrayList<Product>) dbHandler.getProducts();
        cartListAdapters.setProducts(products);
        cartAdapter = new Cart_ProductAdapter(getContext(), products);
        cartListAdapters.setCartAdapter(cartAdapter);

        userCartList= (ListView) view.findViewById(R.id.UserCartList);
        userCartList.setAdapter(cartAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                products = (ArrayList<Product>) dbHandler.getProducts();
                Log.d(TAG, "onClick: products size: " + products.size());
                Order order = new Order();
                order.setProducts(products);
                Intent intent = new Intent(getContext(), OrderMapActivity.class);
                intent.putExtra("newOrder", order);
                startActivity(intent);
                dbHandler.deleteProducts();
                //TODO set the cart num to 0
            }
        });

        return view;
    }

}