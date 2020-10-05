package com.tsofen.onthegoshopClient.DataServices;

public interface OnDataReadyHandler {
    void onDataDownloadCompleted(String downloadedData);
    void onDownloadError();
}
