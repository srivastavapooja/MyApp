package com.pooja.myapp;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.ActionBarOverlayLayout;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class AuthenticationActivity extends AppCompatActivity
implements SignInFragment.OnFragmentInteractionListener, RegisterFragment.OnFragmentInteractionListener,
        ForgotPasswordFragment.OnFragmentInteractionListener, ResetPasswordFragment.OnFragmentInteractionListener

{
    final String TAG = "Pooja's log";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        Toolbar actionbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String action = intent.getStringExtra(intent.EXTRA_TEXT);
        Log.d(TAG, action);
        Fragment fragment = loadFragment(action);

        Log.d(TAG, String.valueOf(fragment.getId()));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void setPageTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    public Fragment loadFragment(String action){
        Log.d(TAG, action);
        switch(action){
            case "signin":
                SignInFragment signin = new SignInFragment();
                Log.d(TAG, String.valueOf(signin.getId()));
                setPageTitle("Login");
                return  signin;
            case "signup":
                RegisterFragment register = new RegisterFragment();
                Log.d(TAG, String.valueOf(register.getId()));
                setPageTitle("Register");
                return register;

        }
        return null;
    }

    public void onFragmentInteraction(Uri uri){

    }
    public void Register(View v){
        RegisterFragment register = new RegisterFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, register);
        transaction.addToBackStack(null);
        setPageTitle("Register");
        // Commit the transaction
        transaction.commit();
    }

    public void AuthenticateSignIn(View v){
        Intent intent = new Intent(this, HomeNavDrawer.class);
        startActivity(intent);
    }

    public void ForgotPassword (View v){
        ForgotPasswordFragment register = new ForgotPasswordFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, register);
        transaction.addToBackStack(null);
        setPageTitle("Forgot Password");
        // Commit the transaction
        transaction.commit();

    }

    public void AuthenticateRegister(View v){
        Intent intent = new Intent(this, HomeNavDrawer.class);
        startActivity(intent);
    }

    public void getVCode(View v){
        ResetPasswordFragment reset = new ResetPasswordFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, reset);
        transaction.addToBackStack(null);
        setPageTitle("Reset Password");
        // Commit the transaction
        transaction.commit();

    }
    public void resetPassword(View v){
        Intent intent = new Intent(this, HomeNavDrawer.class);
        startActivity(intent);
    }
}
