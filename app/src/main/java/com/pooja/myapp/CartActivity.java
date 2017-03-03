package com.pooja.myapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class CartActivity extends AppCompatActivity
implements ViewCartFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Toolbar action_bar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(action_bar);
        getSupportActionBar().setTitle("My Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewCartFragment cart = new ViewCartFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, cart);
        transaction.commit();


    }



    public void onFragmentInteraction(Uri uri){

    }

    public void checkout (View v){
        Intent intent = new Intent(this, CheckoutActivity.class);
        String title = "Checkout";
        intent.putExtra(intent.EXTRA_TEXT, title);
        startActivity(intent);

    }
    public void showCart(View v){
        return;
    }
}
