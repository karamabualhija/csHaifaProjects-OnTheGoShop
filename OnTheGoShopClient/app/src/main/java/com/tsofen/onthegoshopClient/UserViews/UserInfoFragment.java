package com.tsofen.onthegoshopClient.UserViews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tsofen.onthegoshopClient.R;

public class UserInfoFragment extends Fragment {

    public UserInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        TextView name = view.findViewById(R.id.nametv);
        TextView phone = view.findViewById(R.id.phonenumtv);
        TextView username = view.findViewById(R.id.usernametv);

        name.setText(UserMainView.loggedInUser.getName());
        phone.setText(UserMainView.loggedInUser.getPhonenumber());
        username.setText(UserMainView.loggedInUser.getUsername());
        return view;
    }
}