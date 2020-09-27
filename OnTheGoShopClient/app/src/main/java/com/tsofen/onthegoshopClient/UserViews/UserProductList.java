package com.tsofen.onthegoshopClient.UserViews;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.tsofen.onthegoshopClient.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProductList} factory method to
 * create an instance of this fragment.
 */
public class UserProductList extends Fragment {

    private ListView userListView;
    public UserProductList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_product_list, container, false);

        userListView =  (ListView) view.findViewById(R.id.UserProductMainView);
        //TODO create the adapter adn set it to the list.

        return view;
    }
}