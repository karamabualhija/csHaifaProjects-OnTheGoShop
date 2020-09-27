package com.tsofen.onthegoshopClient.UserViews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.tsofen.onthegoshopClient.R;

public class UserInfoFragment extends Fragment {

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //TODO get the logged in info to show them their info.
        return inflater.inflate(R.layout.fragment_user_info, container, false);
    }
}