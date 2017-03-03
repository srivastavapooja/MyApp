package com.pooja.myapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DataSnapshot;

public class ProductListActivity extends AppCompatActivity {

    private final static String TAG = "Pooja's log";
    private ProductListAdapter adapter;
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Toolbar actionbar = (Toolbar) findViewById(R.id.action_bar);
        setSupportActionBar(actionbar);
        Intent intent = getIntent();
        String title = intent.getExtras().getString("LIST_TITLE");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView list = (ListView) findViewById(R.id.listView);
        Item[] items = new Item[0];
        //List<Item> nameslist = new ArrayList(Arrays.asList(items));
        //nameslist = populateList(nameslist);


        adapter = new ProductListAdapter(this,R.layout.productlistlayout, items);
        // adapter.add(nameslist);
        list.setAdapter(adapter);
        //list.setBackgroundColor(getResources().getColor(R.color.colorButtonText));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(ProductListActivity.this, ProductDetailActivity.class);
                intent.putExtra(intent.EXTRA_TEXT, getSupportActionBar().getTitle());
                TextView view = (TextView) v.findViewById(R.id.name);

                intent.putExtra("PRODUCT_NAME", view.getText());
                startActivityForResult(intent, 1);

            }
        });



    }

    @Override
    public void onStart(){
        super.onStart();

        GetProductList getProductList = new GetProductList();
        Intent intent = getIntent();
        String name = intent.getExtras().getString("LIST_TITLE");
        String url = getDataUrl(name);
        Log.d(TAG, "onStart: url = "+url);
        /*mDatabase = FirebaseDatabase.getInstance().getReference("Product List");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                try {
                    String res = (dataSnapshot.child("Product").getValue().toString());
                    JSONArray productListJsonArray = (JSONArray) new JSONTokener(res).nextValue();
                    Log.d(TAG, "doInBackground: firebase " + "= " + productListJsonArray);
                   // JSONArray productListJsonArray = productListJson.getJSONArray("Product");
                    Item[] productlistitems = new Item[productListJsonArray.length()];
                    for (int i = 0; i < productListJsonArray.length(); i++) {
                        JSONObject Jsonitem = productListJsonArray.getJSONObject(i);

                        productlistitems[i] = new Item(Jsonitem.getString("name"), Jsonitem.getString("price"), Jsonitem.getString("ratings"), Jsonitem.getString("img"));
                        Log.d(TAG, "doInBackground: item" + i + "= " + productlistitems[i].print());
                    }

                    //return productlistitems;
                }catch (Exception e){
                    e.printStackTrace();
                }


                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);*/
        getProductList.execute(url);
    }
    public String getDataUrl(String name){
        String url = null;
        switch (name){
            case "Television":
                url = "http://www.avadhlaw.com/pooja/App%20Framework/json/television.json";
                return url;
            case "Refriderator":
                url = "http://www.avadhlaw.com/pooja/App%20Framework/json/refridgerator.json";
                return url;
            case "Microwave":
                url = "http://www.avadhlaw.com/pooja/App%20Framework/json/microwave.json";
                return url;
            case "Automobile":
                url = "http://www.avadhlaw.com/pooja/App%20Framework/json/automobile.json";
                return url;
            default:
                url = "http://www.avadhlaw.com/pooja/App%20Framework/json/television.json";
                return url;
        }
    }

    public void showSortOptions(View v){
        Log.d(TAG, "showPopUp: ");
        AlertDialog.Builder dailog = new AlertDialog.Builder(this);
        dailog.setTitle("Sort By");
       // dailog.setView(R.layout.sortlist);

        final CharSequence[] items = {"Relevence", "Price", "Review"};
        dailog.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        Log.d(TAG, "onClick: case 0");
                        return;
                        case 1:
                            Log.d(TAG, "onClick: case 1");
                            Item[] sorteditems = new Item[adapter.getCount()];
                            sorteditems = adapter.sortbyprice();
                            adapter.clear();
                            adapter.add(sorteditems);
                            adapter.notifyDataSetChanged();
                            return;

                    case 2:
                        Log.d(TAG, "onClick: case 2");
                        return;


                }
            }
        });
        dailog.show();
    }

    public void showFilterOptions(View v){
        Log.d(TAG, "showPopUp: ");
        AlertDialog.Builder dailog = new AlertDialog.Builder(this);
        dailog.setTitle("Filter By");
        dailog.setView(R.layout.filterlist);
        dailog.show();
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
        switch (id) {
            case android.R.id.home:

                Intent intent = getIntent();
                String parent_title = intent.getExtras().getString(intent.EXTRA_TEXT);
                Log.d("Poojas log", parent_title);
                Intent intentforback = getIntent();
                intent.putExtra(intentforback.EXTRA_TEXT, parent_title);
                setResult(Activity.RESULT_OK, intentforback);
                super.onOptionsItemSelected(item);
                finish();
                super.onOptionsItemSelected(item);
                return true;
            case R.id.cart:
                Intent intent1 = new Intent(this, CartActivity.class);
                startActivity(intent1);
            default:
                return true;

        }
        //noinspection SimplifiableIfStatement


    }

    public class GetProductList extends AsyncTask<String, Void, Item[]>{

        @Override
        protected Item[] doInBackground(String... params) {


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
                    Item[] productlistitems = new Item[productListJsonArray.length()];
                    for (int i=0; i< productListJsonArray.length(); i++){
                        JSONObject Jsonitem = productListJsonArray.getJSONObject(i);

                        productlistitems[i] = new Item(Jsonitem.getString("name"), Jsonitem.getString("price"), Jsonitem.getString("ratings"), Jsonitem.getString("img"));
                        Log.d(TAG, "doInBackground: item"+i+"= "+productlistitems[i].print());
                    }

                    return productlistitems;
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
        protected void onPostExecute(Item[] items) {
           // super.onPostExecute(items);

            adapter.clear();
            adapter.add(items);
            adapter.notifyDataSetChanged();
        }
    }

}
