package com.tsofen.onthegoshopClient.UserViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.tsofen.onthegoshopClient.Adapters.FragmentAdapter;
import com.tsofen.onthegoshopClient.Beans.User;
import com.tsofen.onthegoshopClient.R;

public class UserMainView extends AppCompatActivity {

    private ViewPager mViewPager;
    static public User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_view);
        loggedInUser = (User) getIntent().getExtras().get("loggedInUser");
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