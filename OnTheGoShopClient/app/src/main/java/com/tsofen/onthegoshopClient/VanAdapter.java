package com.tsofen.onthegoshopClient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class VanAdapter extends ArrayAdapter<Van> {
    LayoutInflater inflater ;


    public VanAdapter(Context context, ArrayList<Van> vansArray) {
        super(context, 0 , vansArray);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View layout = this.inflater.inflate(R.layout.vans_shape, null);

        Van van = getItem(position);
        TextView vanid = layout.findViewById(R.id.vanid);
        TextView vancap = layout.findViewById(R.id.vanvapacity);
        vanid.setText(van.getId());
        vancap.setText(String.valueOf(van.getCapacity()));
        return layout;
    }
}
