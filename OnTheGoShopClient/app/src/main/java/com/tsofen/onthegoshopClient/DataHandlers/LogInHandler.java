package com.tsofen.onthegoshopClient.DataHandlers;

import com.tsofen.onthegoshopClient.Beans.User;

public interface LogInHandler {
    void OnUserLogIn(User user);
    void OnManagerLogIn();
    void OnDriverLogIn();
    void OnLogInFailure();
}
