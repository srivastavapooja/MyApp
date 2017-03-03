package com.pooja.myapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.BitmapFactory;
import java.net.URI;
import java.net.URL;
import android.net.Uri;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by Dumbledore on 10/26/16.
 */
public class CartAdapter extends ArrayAdapter<Item> {
    private final Activity mContext;
    private final int layout;
    private  Item[] item;
    private static final String TAG = "Pooja's log";
    DisplayImageOptions options;


    public CartAdapter(Activity mContext, int resource, Item[] item) {

        super(mContext,resource, item);
        Log.d(TAG, "CartAdapter: costructor");
        this.mContext = mContext;
        this.layout = resource;
        this.item = item;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_image_placeholder)
                .showImageForEmptyUri(R.drawable.ic_image_placeholder)
                .showImageOnFail(R.drawable.ic_image_placeholder)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
    }


    @Override
    public int getCount() {
        return item.length;
    }

    @Override
    public Item getItem(int position) {
        return item[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: layout = "+layout);
        LayoutInflater inflater=mContext.getLayoutInflater();
        View rowView=inflater.inflate(this.layout, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView price = (TextView) rowView.findViewById(R.id.price);
        EditText quantity = (EditText) rowView.findViewById(R.id.quantity);
        txtTitle.setText(item[position].getName());
        price.setText("Price: " + item[position].getPrice());
        int qty = Float.valueOf(item[position].getRating()).intValue();
        quantity.setText(item[position].getRating());

        final CheckBox remove = (CheckBox) rowView.findViewById(R.id.remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = remove.isChecked();
                if (isChecked){
                    ShoppingCartDB cartDB = new ShoppingCartDB(getContext());
                    SQLiteDatabase db = cartDB.getWritableDatabase();
                    String where = cartDB.getProductName() + "= ?";
                    String[] args = {item[position].getName()};
                    db.delete(cartDB.getTableName(),where, args);
                }
            }
        });


        try {
            URL url = new URL(item[position].getImagesrc());
            ImageLoader.getInstance().displayImage(item[position].getImagesrc(), imageView, options);
            //Log.d(TAG, "getView: img = "+String.valueOf(img.getByteCount()));

            //imageView.setImageResource(imgid[position]);
            //imageView.setImageBitmap(img);
            return rowView;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return rowView;
        }

    }


    public void clear(){
        item = null;
    }
    public void add(Item[] data){
        item = new Item[data.length];
        item = data;
    }
}
