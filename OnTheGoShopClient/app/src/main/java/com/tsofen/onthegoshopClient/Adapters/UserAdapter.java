package com.tsofen.onthegoshopClient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.Beans.User;

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
        TextView name= layout.findViewById(R.id.UserNameshape);
        TextView username= layout.findViewById(R.id.AllUserUsername);
        TextView userph = layout.findViewById(R.id.UserPhoneshape);
        name.setText(user.getName());
        username.setText(user.getUsername());
        userph.setText(String.valueOf(user.getPhonenumber()));
        return layout;
    }


}
