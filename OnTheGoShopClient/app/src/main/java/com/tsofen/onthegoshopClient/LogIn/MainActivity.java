package com.tsofen.onthegoshopClient.LogIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tsofen.onthegoshopClient.BackgroundServices.LocationService;
import com.tsofen.onthegoshopClient.Beans.Driver;
import com.tsofen.onthegoshopClient.Beans.Manager;
import com.tsofen.onthegoshopClient.Beans.User;
import com.tsofen.onthegoshopClient.DataHandlers.LogInHandler;
import com.tsofen.onthegoshopClient.DriverViews.DriverMain;
import com.tsofen.onthegoshopClient.ManagerViews.ManagerMain;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.Register.register;
import com.tsofen.onthegoshopClient.ThreadServices.LogInThread;
import com.tsofen.onthegoshopClient.UserViews.UserMainView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private Boolean mLocationPermissionsGranted = false;

    private EditText usernameEd;
    private EditText passwordEd;
    private TextView errors;
    final private String enterUsername = "Please enter your username";
    final private String combinationError = "Username, password is wrong";
    HandlerThread logInHandlerThread;
    Handler loginHandler;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLocationPermission();

        if (mLocationPermissionsGranted) {
            SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
            if (sharedPreferences.contains("loggedIn")) {
                String sharedUsername = sharedPreferences.getString("username", "");
                String sharedPhoneNum = sharedPreferences.getString("phone", "");
                String sharedName = sharedPreferences.getString("name", "");
                String type = sharedPreferences.getString("userType", "");
                if (type.equals("user")) {
                    User user = new User();
                    user.setUsername(sharedUsername);
                    user.setName(sharedName);
                    user.setPhonenumber(sharedPhoneNum);
                    Intent intent = new Intent(this, UserMainView.class);
                    finishAffinity();
                    startActivity(intent);
                } else if (type.equals("manager")) {
                    Intent intent = new Intent(this, ManagerMain.class);
                    finishAffinity();
                    startActivity(intent);
                } else if (type.equals("driver")) {
                    startLocationService();
                    Intent intent = new Intent(MainActivity.this, DriverMain.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    finishAffinity();
                    startActivity(intent);
                }

            }
        }
    }

    private void startLocationService(){
        if(!isLocationServiceRunning()){
            Intent serviceIntent = new Intent(this, LocationService.class);
//        this.startService(serviceIntent);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){

                MainActivity.this.startForegroundService(serviceIntent);
            }else{
                startService(serviceIntent);
            }
        }
    }

    private boolean isLocationServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if("com.tsofen.onthegoshopClient.BackgroundServices.LocationService".equals(service.service.getClassName())) {
                Log.d(TAG, "isLocationServiceRunning: location service is already running.");
                return true;
            }
        }
        Log.d(TAG, "isLocationServiceRunning: location service is not running.");
        return false;
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                }
            }
        }
    }

    public void logIn(View view) {
        usernameEd = findViewById(R.id.usernameText);
        passwordEd = findViewById(R.id.PasswordText);
        errors = findViewById(R.id.errorTextView);

        if(errors.getVisibility()==View.VISIBLE){
            errors.setVisibility(View.INVISIBLE);
        }
        final String user = usernameEd.getText().toString();
        if(user.isEmpty()){
            errors.setVisibility(View.VISIBLE);
            errors.setText(enterUsername);
            return;
        }
        final String pass = passwordEd.getText().toString();

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        hideKeyboard(this);

        logInHandlerThread =  new HandlerThread("logInHandlerThread");
        logInHandlerThread.start();
        loginHandler = new Handler(logInHandlerThread.getLooper());

        LogInThread logInThread = new LogInThread(user, pass, new LogInHandler() {
            @Override
            public void OnUserLogIn(final User user) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", user.getUsername());
                        editor.putString("name", user.getName());
                        editor.putString("phone", user.getPhonenumber());
                        editor.putString("id", String.valueOf(user.getSys_id()));
                        editor.putString("userType", "user");
                        editor.putBoolean("loggedIn", true);
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, UserMainView.class);
                        finishAffinity();
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void OnManagerLogIn(final Manager manager) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", manager.getUsername());
                        editor.putString("name", manager.getName());
                        editor.putString("phone", manager.getPhonenumber());
                        editor.putString("id", String.valueOf(manager.getSys_id()));
                        editor.putString("userType", "manager");
                        editor.putBoolean("loggedIn", true);
                        editor.apply();
                        Intent intent = new Intent(MainActivity.this, ManagerMain.class);
                        finishAffinity();
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void OnDriverLogIn(final Driver driver) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences sharedPreferences = getSharedPreferences("login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", driver.getUsername());
                        editor.putString("name", driver.getName());
                        editor.putString("phone", driver.getPhonenumber());
                        editor.putString("vanNum", driver.getVanNum());
                        editor.putString("userType", "driver");
                        editor.putString("id", driver.getId());
                        editor.putBoolean("loggedIn", true);
                        editor.apply();
                        startLocationService();
                        Intent intent = new Intent(MainActivity.this, DriverMain.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        finishAffinity();
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void OnLogInFailure() {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         errors.setVisibility(View.VISIBLE);
                         errors.setText(combinationError);
                         progressBar.setVisibility(View.INVISIBLE);
                     }
                 });
            }
        });
        loginHandler.post(logInThread);
    }


    public void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void goToForgotPassword(View view) {
        Toast.makeText(this,"in the making", Toast.LENGTH_LONG).show();

    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this, register.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (logInHandlerThread!=null) {
            logInHandlerThread.quit();
        }
    }
}