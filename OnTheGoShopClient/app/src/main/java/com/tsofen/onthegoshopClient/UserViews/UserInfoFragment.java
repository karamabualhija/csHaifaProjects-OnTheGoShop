package com.tsofen.onthegoshopClient.UserViews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tsofen.onthegoshopClient.LogIn.MainActivity;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.LogoutThread;

import static android.content.Context.MODE_PRIVATE;

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
        Button logout = view.findViewById(R.id.logoutBtn);
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("login", MODE_PRIVATE);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut(view);
            }
        });

        name.setText(sharedPreferences.getString("name", ""));
        phone.setText(sharedPreferences.getString("phone", ""));
        username.setText(sharedPreferences.getString("username", ""));
        return view;
    }

    private void logOut(View view) {
        SharedPreferences sharedPreferences = view.getContext().getSharedPreferences("login", MODE_PRIVATE);
        SharedPreferences.Editor edit  = sharedPreferences.edit();
        edit.remove("loggedIn");
        edit.apply();

        HandlerThread logoutHandlerThread = new HandlerThread("logoutHandlerThread");
        logoutHandlerThread.start();
        Handler handler =  new Handler(logoutHandlerThread.getLooper());
        handler.post(new LogoutThread());

        Intent intent = new Intent(view.getContext(), MainActivity.class);
        Activity host = (Activity)view.getContext();
        host.finishAffinity();
        startActivity(intent);
    }

}