package com.tsofen.onthegoshopClient.DataHandlers;

public interface NewProductHandler {
    void onProductAdded();
    void onProductNotAdded();
    void onFailure();
}
