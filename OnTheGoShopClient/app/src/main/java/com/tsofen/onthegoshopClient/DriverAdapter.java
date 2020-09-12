package com.tsofen.onthegoshopClient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class DriverAdapter extends ArrayAdapter<Driver> {
    LayoutInflater inflater;
    public  DriverAdapter(Context context,ArrayList<Driver> DriversArray)
    {
        super(context,0,DriversArray);
        inflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View layout=this.inflater.inflate(R.layout.driver_shape,null);
        Driver driver=getItem(position);
        TextView Drivername=layout.findViewById(R.id.DriverName);
        TextView Driverphone=layout.findViewById(R.id.DriverPhone);
        Drivername.setText(driver.getName());
        Driverphone.setText(driver.getPhonenumber());

        return layout;

    }


}
