package com.pooja.myapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import com.nostra13.universalimageloader.core.ImageLoader;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.StringTokenizer;

public class ProductDetailActivity extends AppCompatActivity
implements ProductReviewFragment.OnFragmentInteractionListener, ProductInfoFragment.OnFragmentInteractionListener{

    private static final String TAG = "Pooja's log";
    private ProductDetail product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Toolbar actionbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(actionbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TabLayout tab = (TabLayout) findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("Details"));
        tab.addTab(tab.newTab().setText("Reviews"));

        final ViewPager page = (ViewPager) findViewById(R.id.view_pager);

        DetailPageAdapter pageadapter = new DetailPageAdapter(getSupportFragmentManager() );

        page.setAdapter(pageadapter);
        page.addOnPageChangeListener((new TabLayout.TabLayoutOnPageChangeListener(tab)));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: ");
                page.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        ProductInfoFragment info = new ProductInfoFragment();
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.add(R.id.view_pager, info);
                        transaction1.commit();
                        return;
                    case 1:
                        //Bundle bundle = new Bundle();
                        //bundle.putCharSequenceArray("reviews", product.getReviews());
                        ProductReviewFragment frag = ProductReviewFragment.newInstance(null,null, product.getReviews());
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.add(R.id.view_pager, frag);
                        transaction.commit();
                        return;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });


    }
    @Override
    public void onStart(){
        super.onStart();
        Intent intent = getIntent();
        String product = intent.getExtras().getString("PRODUCT_NAME");

        fetchProductDetails(product);
    }

    public void fetchProductDetails(String product){
        Log.d(TAG, "fetchProductDetails: product = "+product);
         String url = getURL(product);
        Log.d(TAG, "fetchProductDetails: url = "+url);
        DownloadProductDetails details = new DownloadProductDetails();
        details.execute(url);


    }

    public String getURL(String product){
        switch (product){
            case "Samsung UCZJ000-LED50inch":
                return "http://www.avadhlaw.com/pooja/App%20Framework/json/tv_samsung.json";
            case "GE® 1.4 Cu. Ft. Countertop Microwave":
                return "http://www.avadhlaw.com/pooja/App%20Framework/json/microwave_ge.json";
            default:
                return "http://www.avadhlaw.com/pooja/App%20Framework/json/tv_samsung.json";
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_detail_nav_drawer, menu);

        return true;
    }


    @Override
    public void onBackPressed(){
        Log.d("Poojas log", "onBackPressed: ");
        Intent intent = getIntent();
        String parent_title = intent.getExtras().getString(intent.EXTRA_TEXT);

        Intent intentforback = getIntent();
        intent.putExtra(intentforback.EXTRA_TEXT,parent_title);
        setResult(RESULT_OK, intentforback);
        super.onBackPressed();
        finish();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                Intent intent = getIntent();
                String parent_title = intent.getExtras().getString(intent.EXTRA_TEXT);
                Log.d("Poojas log", parent_title);
                Intent intentforback = getIntent();
                intent.putExtra(intentforback.EXTRA_TEXT,parent_title);
                setResult(Activity.RESULT_OK, intentforback);
                super.onOptionsItemSelected(item);
                finish();
                return true;
            case R.id.cart:
                Intent intent2 = new Intent(this, CartActivity.class);
                startActivity(intent2);
                super.onOptionsItemSelected(item);
                return true;
            case R.id.share:
                Intent shareintent = new Intent();
                String text = "Product: "+product.getName()+" Price: "+product.getPrice()+" Rating: "+product.getRating();
                shareintent.setAction(Intent.ACTION_SEND);
                shareintent.setType("text/plain");
                shareintent.putExtra(Intent.EXTRA_TEXT,text);
                startActivity(Intent.createChooser(shareintent, "Share image using"));


        }
        return true;
    }

    public void addToCart(View v){
        Context context = getLayoutInflater().getContext();
        ShoppingCartDB cartDB = new ShoppingCartDB(context);
        SQLiteDatabase db = cartDB.getWritableDatabase();
        String[] result = {cartDB.getProductName(), cartDB.getQuantity()};
        String where = cartDB.getProductName() + "= ?";
        String[] args = {product.getName()};
        Cursor c = db.query(cartDB.getTableName(),                     // The table to query
                result,                               // The columns to return
                where,
                args,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
        ContentValues value = new ContentValues();
        Log.d(TAG, "addToCart: cursor count = "+c.getCount());
        if(c.getCount()>=1){
            c.moveToFirst();
            int quantity = c.getInt(c.getColumnIndexOrThrow(cartDB.getQuantity()))+1;
            Log.d(TAG, "addToCart: quantity = "+ quantity);
            value.put(cartDB.getQuantity(), quantity);

// Which row to update, based on the title
            String selection = cartDB.getProductName() + " = ?";
            String[] selectionArgs = { c.getString(c.getColumnIndexOrThrow(cartDB.getProductName())) };

            int count = db.update(
                    cartDB.getTableName(),
                    value,
                    selection,
                    selectionArgs);

        }else {
            value.put(cartDB.getProductName(), product.getName());
            value.put(cartDB.getPrice(), product.getPrice());
            value.put(cartDB.getQuantity(), 1);
            value.put(cartDB.getImage(), product.getImageurl());
            db.insert(cartDB.getTableName(), null, value);

        }

    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public static class DetailPageAdapter extends FragmentStatePagerAdapter{

        public DetailPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    ProductInfoFragment detailtab = new ProductInfoFragment();
                    return detailtab;
                case 1:
                    ProductReviewFragment reviews = new ProductReviewFragment();
                    return reviews;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

    public class DownloadProductDetails extends AsyncTask<String, Void, ProductDetail> {

        @Override
        protected ProductDetail doInBackground(String... params) {
            HttpURLConnection httpURLConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                InputStream data = httpURLConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(data));
                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                String productdetails = buffer.toString();
                try{
                    JSONObject productJson = new JSONObject(productdetails).getJSONObject("Product");
                    String name = productJson.getString("name");
                    String imageurl = productJson.getString("image");
                    String price = productJson.getString("price");
                    String rating = productJson.getString("rating");
                    JSONArray reviews = productJson.getJSONArray("reviews");
                    Log.d(TAG, "doInBackground: jsonarray review "+ reviews.length());
                    String[] rev = new String[reviews.length()];
                    for (int i=0; i< reviews.length();i++){
                        rev[i] = reviews.getString(i);
                        Log.d(TAG, "doInBackground: revstring "+rev[i]);
                    }
                    product = new ProductDetail(name,imageurl,price, rating,rev);

                    String[] str = Arrays.copyOf(product.getReviews(), product.getReviews().length);
                    Log.d(TAG, "doInBackground: pd = "+product.print());
                    Log.d(TAG, "doInBackground: str = "+str[0]);
                    return product;
                }catch (Exception e){
                    e.printStackTrace();
                }

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                Log.d(TAG, "doInBackground: finally");
                httpURLConnection.disconnect();
            }
            return null;
        }

        @Override
        public void onPostExecute(ProductDetail detail) {
            super.onPostExecute(detail);
            TextView pname = (TextView) findViewById(R.id.product_detail);
            TextView pprice = (TextView) findViewById(R.id.product_price);
            TextView prating = (TextView) findViewById(R.id.product_rating);
            ImageView pimage = (ImageView) findViewById(R.id.product_img);

            pname.setText(detail.getName());
            pprice.setText("Price: $" + detail.getPrice());
            prating.setText("Rating: "+detail.getRating());
            ImageLoader.getInstance().displayImage(detail.getImageurl(), pimage);

        }
    }

}
