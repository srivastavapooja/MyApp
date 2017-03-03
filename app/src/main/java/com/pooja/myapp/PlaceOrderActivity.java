package com.pooja.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PlaceOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);

        Toolbar actionbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(actionbar);
        getSupportActionBar().setTitle("Place Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void showCart(View v){
        return;
    }

}
