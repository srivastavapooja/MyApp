package com.pooja.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CheckoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        Toolbar actionbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(actionbar);
        getSupportActionBar().setTitle("Checkout");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void reviewOrder(View v){
        Intent intent = new Intent(this, PlaceOrderActivity.class);
        startActivity(intent);

    }
    public void showCart(View v){
        return;
    }

}
