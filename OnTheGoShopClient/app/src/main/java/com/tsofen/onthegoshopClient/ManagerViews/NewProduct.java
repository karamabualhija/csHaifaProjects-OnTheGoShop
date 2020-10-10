package com.tsofen.onthegoshopClient.ManagerViews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tsofen.onthegoshopClient.DataHandlers.NewProductHandler;
import com.tsofen.onthegoshopClient.R;
import com.tsofen.onthegoshopClient.ThreadServices.NewProductThread;

public class NewProduct extends AppCompatActivity {

    private static final String TAG = "NewProduct";

    private EditText newProductName;
    private EditText newProductAmount;
    private EditText newProductPrice;

    private HandlerThread newProductHandlerThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        newProductName = findViewById(R.id.newProductName);
        newProductAmount = findViewById(R.id.newProductAmount);
        newProductPrice = findViewById(R.id.newProductPrice);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, ManagerStorage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void addNewProduct(View view) {

        if (newProductPrice.getText().toString().isEmpty()){
            Toast.makeText(this, "please enter the product Price", Toast.LENGTH_LONG).show();
            return;
        }
        if (newProductAmount.getText().toString().isEmpty()){
            Toast.makeText(this, "please enter the product Amount", Toast.LENGTH_LONG).show();
            return;
        }
        if (newProductName.getText().toString().isEmpty()){
            Toast.makeText(this, "please enter the product Name", Toast.LENGTH_LONG).show();
            return;
        }

        String name =  newProductName.getText().toString();
        double amount = Double.parseDouble(newProductAmount.getText().toString());
        float price = Float.parseFloat(newProductPrice.getText().toString());

        newProductHandlerThread = new HandlerThread("newProductHandlerThread");
        newProductHandlerThread.start();

        NewProductThread newProductThread = new NewProductThread(name, price, amount, new NewProductHandler() {
            @Override
            public void onProductAdded() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(NewProduct.this, ManagerStorage.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure() {
                Toast.makeText(NewProduct.this, "failed to add the product try again", Toast.LENGTH_LONG).show();
            }
        });

        Handler handler = new Handler(newProductHandlerThread.getLooper());
        handler.post(newProductThread);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (newProductHandlerThread!=null && newProductHandlerThread.isAlive()){
            newProductHandlerThread.quit();
        }
    }
}