package com.tsofen.onthegoshopClient.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.Beans.Van;

import java.util.ArrayList;

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
