package com.tsofen.onthegoshopClient.DataHandlers;

import com.tsofen.onthegoshopClient.Beans.User;

import java.util.ArrayList;

public interface AllUsersHandler {
    void onUserReceived(ArrayList<User> userArrayList);
    void onUsersFailure();
}
