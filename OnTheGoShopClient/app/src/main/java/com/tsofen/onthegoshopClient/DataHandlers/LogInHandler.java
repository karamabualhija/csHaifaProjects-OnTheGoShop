package com.tsofen.onthegoshopClient.DataHandlers;

import com.tsofen.onthegoshopClient.Beans.Driver;
import com.tsofen.onthegoshopClient.Beans.Manager;
import com.tsofen.onthegoshopClient.Beans.User;

public interface LogInHandler {
    void OnUserLogIn(User user);
    void OnManagerLogIn(Manager manager);
    void OnDriverLogIn(Driver driver);
    void OnLogInFailure();
}
