package com.tsofen.onthegoshopClient.UserViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tsofen.onthegoshopClient.Adapters.FragmentAdapter;
import com.tsofen.onthegoshopClient.Beans.Order;
import com.tsofen.onthegoshopClient.Beans.Product;
import com.tsofen.onthegoshopClient.Beans.User;
import com.tsofen.onthegoshopClient.DBHandler.CartDBHandler;
import com.tsofen.onthegoshopClient.R;

import java.util.ArrayList;

public class UserMainView extends AppCompatActivity {

    private static final String TAG = "UserMainView";

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_view);
        mViewPager = (ViewPager) findViewById(R.id.userFragmentContainer);
        //setup the pager
        setupViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager){
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserProductList(), "UserHomeView");
        adapter.addFragment(new UserCartFragment(), "UserCartView");
        adapter.addFragment(new UserOrderFragment(), "UserOrderView");
        adapter.addFragment(new UserInfoFragment(), "UserInfoView");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }

    public void setHomeFragment(View view) {
        setViewPager(0);
    }
    public void setCartFragment(View view) {
        setViewPager(1);
    }
    public void setOrderFragment(View view) {
        setViewPager(2);
    }
    public void setInfoFragment(View view) {
        setViewPager(3);
    }

    public void addProductToCart(View v){
        LinearLayout rl = (LinearLayout) v.getParent();
        TextView productNameTv = (TextView)rl.findViewById(R.id.productName);
        TextView productPriceTv = rl.findViewById(R.id.productprice);
        TextView productIdTv = rl.findViewById(R.id.productId);
        String productPrice = productPriceTv.getText().toString();
        String productName = productNameTv.getText().toString();
        String productId = productIdTv.getText().toString();

        Product product = new Product();
        product.setName(productName);
        product.setPrice(Float.parseFloat(productPrice));
        product.setId(Integer.parseInt(productId));

        CartDBHandler cartDBHandler = new CartDBHandler(this, null, 1);
        if(!cartDBHandler.addProduct(product)){
            Toast.makeText(this, "you have already added the item to the cart", Toast.LENGTH_SHORT).show();
            return;
        }
        //TODO add code to update the number on the cart;
        CartListAdapters cartListAdapters = CartListAdapters.getInstance();
        cartListAdapters.getProducts().add(product);
        cartListAdapters.getCartAdapter().notifyDataSetChanged();
        Log.d(TAG, "AddCartProduct: update cart size");
    }

    public void deleteProductFromCart(View v){
        LinearLayout linearLayout = (LinearLayout) v.getParent();
        TextView nameTv = linearLayout.findViewById(R.id.productNameCart);
        String productName = nameTv.getText().toString();
        Log.d(TAG, "deleteProductFromCart: the product name is: " + productName);
        CartDBHandler dbHandler = new CartDBHandler(this, null, 1);
        dbHandler.deleteProduct(productName);
        CartListAdapters cartListAdapters = CartListAdapters.getInstance();
        ArrayList<Product> products = cartListAdapters.getProducts();
        for (Product pro:
             products) {
            if (pro.getName().equals(productName)) {
                Log.d(TAG, "deleteProductFromCart: i have found the product");
                products.remove(pro);
            }
        }
        cartListAdapters.getCartAdapter().notifyDataSetChanged();
        //TODO update the num on cart
        Log.d(TAG, "deleteCartProduct: update cart size");
    }

    public void orderDetails(View v){
        LinearLayout linearLayout = (LinearLayout) v.getParent();
        TextView orderIdTv = linearLayout.findViewById(R.id.orderid);
        TextView orderPriceTv = linearLayout.findViewById(R.id.orderprice);

        String orderID= orderIdTv.getText().toString();
        String orderPrice= orderPriceTv.getText().toString();
        Intent intent = new Intent(this, OrderDetails.class);
        intent.putExtra("orderID", orderID);
        intent.putExtra("orderPrice", orderPrice);
        startActivity(intent);
    }

}