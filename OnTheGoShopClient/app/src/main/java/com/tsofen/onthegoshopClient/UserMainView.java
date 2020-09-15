package com.tsofen.onthegoshopClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

public class UserMainView extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}