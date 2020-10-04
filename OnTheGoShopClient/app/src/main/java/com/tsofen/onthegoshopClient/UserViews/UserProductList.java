package com.tsofen.onthegoshopClient.UserViews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.tsofen.onthegoshopClient.Adapters.ProductAdapter;
import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.DBHandler.CartDBHandler;
import com.tsofen.onthegoshopClient.DataHandlers.AllProductsHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.AllProductsThread;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProductList} factory method to
 * create an instance of this fragment.
 */
public class UserProductList extends Fragment {

    private static final String TAG = "UserProductList";

    private ListView userListView;
    private Button addToCart;
    private CartDBHandler cartDB;
    private ProductAdapter productAdapter;
    public UserProductList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_product_list, container, false);
        cartDB = new CartDBHandler(getContext(), null , 1);
        userListView = view.findViewById(R.id.UserProductMainView);
        addToCart = view.findViewById(R.id.AddButton);

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getId();
                Product product = null;
                Object obj = userListView.getItemAtPosition(position);
                if(obj instanceof Product){
                    product = (Product) obj;
                }
                if(product!=null){
                    if(!cartDB.addProduct(product)){
                        Toast.makeText(getContext(), "you have already added the item to the cart", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //TODO add code to update the number on the cart;
                    Log.d(TAG, "onClick: update cart size");
                }
            }
        });

        AllProductsThread getProducts = new AllProductsThread(new AllProductsHandler() {
            @Override
            public void onDataReceived(final ArrayList<Product> products) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setProductAdapter(products);
                    }
                });
            }
        });
        Handler handler = new Handler();
        handler.post(getProducts);
        return view;
    }

    public void setProductAdapter(ArrayList<Product> p){
        productAdapter = new ProductAdapter(this.getContext(), p);
        userListView.setAdapter(productAdapter);
    }
}