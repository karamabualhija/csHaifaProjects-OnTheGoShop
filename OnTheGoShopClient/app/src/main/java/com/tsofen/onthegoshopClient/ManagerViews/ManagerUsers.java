package com.tsofen.onthegoshopClient.ManagerViews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tsofen.onthegoshopClient.Adapters.UserAdapter;
import com.tsofen.onthegoshopClient.Beans.User;
import com.tsofen.onthegoshopClient.DataHandlers.AllUsersHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.AllUsersThread;

import java.util.ArrayList;

public class ManagerUsers extends AppCompatActivity {

    private static final String TAG = "ManagerUsers";

    private ListView userListV;
    private UserAdapter userAdapter;
    private ArrayList<User> users;
    private HandlerThread managerUserHandlerThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_users);

        userListV = findViewById(R.id.managerUsersList);

        managerUserHandlerThread = new HandlerThread("managerUserHandlerThread");
        managerUserHandlerThread.start();

        AllUsersThread allUsersThread = new AllUsersThread(new AllUsersHandler() {
            @Override
            public void onUserReceived(final ArrayList<User> userArrayList) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setUsersList(userArrayList);
                    }
                });
            }

            @Override
            public void onUsersFailure() {
                Log.d(TAG, "onUsersFailure: Failed to get the users");
            }
        });

        Handler handler = new Handler(managerUserHandlerThread.getLooper());
        handler.post(allUsersThread);
    }

    private void setUsersList(ArrayList<User> userArrayList) {
        this.users = userArrayList;
        userAdapter = new UserAdapter(this, users);
        userListV.setAdapter(userAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (managerUserHandlerThread!=null && managerUserHandlerThread.isAlive())
            managerUserHandlerThread.quit();
    }

    public void getUserOrder(View v){
        LinearLayout linearLayout = (LinearLayout) v.getParent();
        TextView name = linearLayout.findViewById(R.id.AllUserUsername);

        Log.d(TAG, "getUserOrder: requesting the orders of: " + name.getText().toString());
        Intent intent = new Intent(this, ManagerUserOrders.class);
        intent.putExtra("username", name.getText().toString());
        startActivity(intent);

    }
}