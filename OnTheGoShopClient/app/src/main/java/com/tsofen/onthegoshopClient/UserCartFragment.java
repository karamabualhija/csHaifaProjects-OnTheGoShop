package com.tsofen.onthegoshopClient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


public class UserCartFragment extends Fragment {

    ListView userCartList;

    public UserCartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_cart, container, false);

        userCartList= (ListView) view.findViewById(R.id.UserCartList);
        //TODO create the adapter for the cart list. it should be for the product adapter.
        return view;
    }
}