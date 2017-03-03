package com.pooja.myapp;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;


public class CatalogListActivity extends AppCompatActivity
{
    final static String TAG = "Pooja's log";
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_list);

        Toolbar action_bar = (Toolbar) findViewById(R.id.action_bar);

        setSupportActionBar(action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = this.getIntent();
        Bundle extras = intent.getExtras();
        Log.d(TAG, extras.getString(intent.EXTRA_TEXT));

        getSupportActionBar().setTitle(extras.getString(intent.EXTRA_TEXT));

        ListView list = (ListView) findViewById(R.id.listView);
        String[] names = new String[0];
        List<String> nameslist = new ArrayList(Arrays.asList(names));
        //nameslist = populateList(nameslist);
        for (int j=0; j< nameslist.size();j++){
            Log.d(TAG, nameslist.get(j).toString());
        }


        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nameslist);
       // adapter.add(nameslist);
        list.setAdapter(adapter);
        //list.setBackgroundColor(getResources().getColor(R.color.colorButtonText));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(CatalogListActivity.this, ProductListActivity.class);
                intent.putExtra(intent.EXTRA_TEXT, getSupportActionBar().getTitle());
                String key = parent.getItemAtPosition(position).toString();
                Log.d(TAG, "onItemClick: key ="+key);
                intent.putExtra("LIST_TITLE", key);
                startActivityForResult(intent, 1);

            }
        });



    }

    @Override
    public void onStart(){
        super.onStart();
        Intent intent = getIntent();
        String categoryname = intent.getExtras().getString(intent.EXTRA_TEXT);
        String dataUrl = getDataUrl(categoryname);
        GetCatalogList catalogList = new GetCatalogList();

        catalogList.execute(dataUrl);
    }

    public String getDataUrl(String name){
        String url = null;
        switch (name){
            case "Electronics":
                url = "http://www.avadhlaw.com/pooja/App%20Framework/json/electronics.json";
                return url;
            case "Arts":
                url = "http://www.avadhlaw.com/pooja/App%20Framework/json/art.json";
                return url;
            case "Clothes":
                url = "http://www.avadhlaw.com/pooja/App%20Framework/json/clothes.json";
                return url;
            case "Automobile":
                url = "http://www.avadhlaw.com/pooja/App%20Framework/json/automobile.json";
                return url;
            default:
                url = "http://www.avadhlaw.com/pooja/App%20Framework/json/electronics.json";
                return url;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_nav_drawer, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cart) {
            Intent intent = new Intent(this, CartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){

                String title =data.getStringExtra(data.EXTRA_TEXT);
               // String title2 = data.getExtras().getString(data.EXTRA_TEXT);
                Log.d(TAG, "onActivityResult: "+title);
                getSupportActionBar().setTitle(title);
            }
        }
    }

    public class GetCatalogList extends AsyncTask<String, Void, String[]>{

        @Override
        protected String[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream data = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(data));
                StringBuffer buffer = new StringBuffer();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                String productlist = buffer.toString();
                try{
                    JSONObject productListJson = new JSONObject(productlist).getJSONObject("Product List");

                    JSONArray productListJsonArray = productListJson.getJSONArray("Product");
                    String[] productlistString = new String[productListJsonArray.length()];
                    for (int i=0; i< productListJsonArray.length(); i++){
                        JSONObject item = productListJsonArray.getJSONObject(i);
                        String itemStr = item.getString("name");
                        productlistString[i] = itemStr;
                    }

                    return productlistString;
                }catch (Exception e){
                    e.printStackTrace();
                }

            }catch(Exception e){
                e.printStackTrace();
            }finally {
                urlConnection.disconnect();
            }
            return null;

        }

        @Override
        protected void onPostExecute(String[] str){
            if(adapter.getCount()!=0) {
                adapter.clear();
            }
            for(int i=0;i<str.length;i++){
                adapter.add(str[i]);
            }
        }
    }

}
