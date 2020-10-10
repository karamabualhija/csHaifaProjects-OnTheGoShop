package com.tsofen.onthegoshopClient.DataHandlers;

import com.google.android.gms.maps.model.LatLng;

public interface DriverLocationHandler {
    void onLocationReceived(LatLng latLng);
    void onFailure();
}
