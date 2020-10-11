package com.tsofen.onthegoshopClient.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.EditText;

import com.tsofen.onthegoshopClient.Beans.User;
import com.tsofen.onthegoshopClient.DataHandlers.RegisterHandler;
import com.tsofen.onthegoshopClient.LogIn.MainActivity;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.RegisterThread;

public class register extends AppCompatActivity {

    HandlerThread registerHandlerThread;
    Handler registerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {

        registerHandlerThread = new HandlerThread("registerHandlerThread");
        registerHandlerThread.start();
        registerHandler = new Handler(registerHandlerThread.getLooper());

        EditText nameEd = findViewById(R.id.fullname);
        EditText phoneNumEd = findViewById(R.id.phoneNumber);
        EditText usernameEd = findViewById(R.id.username);
        EditText passEd = findViewById(R.id.Password);
        EditText passConEd = findViewById(R.id.confirmPassword);

        findViewById(R.id.nameRequired).setVisibility(View.INVISIBLE);
        findViewById(R.id.phoneRequired).setVisibility(View.INVISIBLE);
        findViewById(R.id.usernameRequired).setVisibility(View.INVISIBLE);
        findViewById(R.id.passRequired).setVisibility(View.INVISIBLE);
        findViewById(R.id.passConRequired).setVisibility(View.INVISIBLE);

        final String name = nameEd.getText().toString();
        final String phone = phoneNumEd.getText().toString();
        final String user = usernameEd.getText().toString();
        final String pass = passEd.getText().toString();
        final String passCon = passConEd.getText().toString();
        if (name.isEmpty()){
            findViewById(R.id.nameRequired).setVisibility(View.VISIBLE);
            return;
        }
        if(phone.isEmpty()){
            findViewById(R.id.phoneRequired).setVisibility(View.VISIBLE);
            return;
        }
        if (user.isEmpty()){
            findViewById(R.id.usernameRequired).setVisibility(View.VISIBLE);
            return;
        }
        if (pass.isEmpty()){
            findViewById(R.id.passRequired).setVisibility(View.VISIBLE);
            return;
        }
        if (!pass.equals(passCon)){
            findViewById(R.id.passConRequired).setVisibility(View.VISIBLE);
            return;
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setUsername(user);
        newUser.setPassword(pass);
        newUser.setPhonenumber(phone);

        RegisterThread registerThread = new RegisterThread(newUser, new RegisterHandler() {
            @Override
            public void onRegisterSuccess() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                });
            }

            @Override
            public void onRegisterFailure() {

            }
        });
        registerHandler.post(registerThread);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registerHandlerThread.isAlive())
            registerHandlerThread.quit();
    }
}