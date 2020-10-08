package com.tsofen.onthegoshopClient.DataHandlers;

import com.tsofen.onthegoshopClient.Beans.Driver;

import java.util.ArrayList;

public interface AllDriverHandler {
    void onDriversReceived(ArrayList<Driver> driverArrayList);
}
