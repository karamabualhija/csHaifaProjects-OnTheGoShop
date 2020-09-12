package com.tsofen.onthegoshopClient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {
    LayoutInflater inflater ;


    public UserAdapter(Context context, ArrayList<User> usersArray) {
        super(context, 0 , usersArray);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View layout = this.inflater.inflate(R.layout.user_shape, null);
        User user = getItem(position);
        TextView username= layout.findViewById(R.id.UserNameshape);
        TextView userph = layout.findViewById(R.id.UserPhoneshape);
        username.setText(user.getName());
        userph.setText(String.valueOf(user.getPhonenumber()));
        return layout;
    }


}
