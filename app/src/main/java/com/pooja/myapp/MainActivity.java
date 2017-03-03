package com.pooja.myapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
                .diskCacheExtraOptions(480, 800, null)
                .threadPoolSize(3) // default
                .threadPriority(Thread.NORM_PRIORITY - 2) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);

    }

    public void SignIn (View v){
        Intent intent = new Intent(this,AuthenticationActivity.class);
        intent.putExtra(intent.EXTRA_TEXT, "signin");
        startActivity(intent);

    }

    public void SignUp (View v){
        Intent intent = new Intent(this,AuthenticationActivity.class);
        intent.putExtra(intent.EXTRA_TEXT, "signup");
        startActivity(intent);

    }
    public void ContinueToHome (View v){
        Intent intent = new Intent(this,HomeNavDrawer.class);
        intent.putExtra(intent.EXTRA_TEXT, "continue");
        startActivity(intent);

    }
}
