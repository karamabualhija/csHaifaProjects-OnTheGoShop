package com.tsofen.onthegoshopClient.ManagerViews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tsofen.onthegoshopClient.DataHandlers.NewDriverHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.NewDriverThread;

public class NewDriver extends AppCompatActivity {

    private static final String TAG = "NewDriver";

    private EditText newDriverName;
    private EditText newDriverUserName;
    private EditText newDriverPhone;
    private EditText newDriverPassword;
    private EditText newDriverPasswordConf;
    private EditText newDriverVanNum;

    private HandlerThread newDriverHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_driver);

        newDriverName = findViewById(R.id.newDriverName);
        newDriverUserName = findViewById(R.id.newDriverUsername);
        newDriverPassword = findViewById(R.id.newDriverPassword);
        newDriverPasswordConf = findViewById(R.id.newDriverConfirmPass);
        newDriverPhone = findViewById(R.id.newDriverPhone);
        newDriverVanNum = findViewById(R.id.newDriverVan);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ManagerDrivers.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void addNewDriver(View view) {

        if (newDriverName.getText().toString().isEmpty()){
            Toast.makeText(this, "please enter the driver name", Toast.LENGTH_LONG).show();
            return;
        }
        if (newDriverUserName.getText().toString().isEmpty()){
            Toast.makeText(this, "please enter the driver user name", Toast.LENGTH_LONG).show();
            return;
        }
        if (newDriverPhone.getText().toString().isEmpty()){
            Toast.makeText(this, "please enter the driver phone num", Toast.LENGTH_LONG).show();
            return;
        }
        if (newDriverPassword.getText().toString().isEmpty()){
            Toast.makeText(this, "please enter the driver password", Toast.LENGTH_LONG).show();
            return;
        }
        if (newDriverPasswordConf.getText().toString().isEmpty()){
            Toast.makeText(this, "please enter the driver password confirm", Toast.LENGTH_LONG).show();
            return;
        }
        if (newDriverVanNum.getText().toString().isEmpty()){
            Toast.makeText(this, "please enter the driver van number", Toast.LENGTH_LONG).show();
            return;
        }
        if (!newDriverPassword.getText().toString().equals(newDriverPasswordConf.getText().toString())){
            Toast.makeText(this, "please enter the same password", Toast.LENGTH_LONG).show();
            return;
        }

        String name = newDriverName.getText().toString();
        String phonenumber = newDriverPhone.getText().toString();
        String username = newDriverUserName.getText().toString();
        String password = newDriverPassword.getText().toString();
        int vanNum = Integer.parseInt(newDriverVanNum.getText().toString());

        newDriverHandlerThread = new HandlerThread("newDriverHandlerThread");
        newDriverHandlerThread.start();

        NewDriverThread newDriverThread = new NewDriverThread(name, phonenumber, username, password, vanNum,
                new NewDriverHandler() {
                    @Override
                    public void onDriverAdded() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(NewDriver.this, ManagerDrivers.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                finishAffinity();
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        Log.d(TAG, "onFailure: failed to add new Driver");
                        Toast.makeText(NewDriver.this, "Failed to add driver please try again", Toast.LENGTH_LONG).show();
                    }
                });
        Handler handler = new Handler(newDriverHandlerThread.getLooper());
        handler.post(newDriverThread);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (newDriverHandlerThread!=null && newDriverHandlerThread.isAlive()){
            newDriverHandlerThread.quit();
        }
    }
}