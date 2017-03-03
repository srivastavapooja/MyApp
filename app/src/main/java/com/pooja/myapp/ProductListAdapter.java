package com.pooja.myapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
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
public class ProductListAdapter extends ArrayAdapter<Item> {
    private final Activity mContext;
    private final int layout;
    private  Item[] item;
    private static final String TAG = "Pooja's log";
    DisplayImageOptions options;


    public ProductListAdapter(Activity mContext, int resource, Item[] item) {

        super(mContext,resource, item);
        Log.d(TAG, "ProductListAdapter: costructor");
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
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: layout = "+layout);
        LayoutInflater inflater=mContext.getLayoutInflater();
        View rowView=inflater.inflate(this.layout, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView price = (TextView) rowView.findViewById(R.id.price);
        TextView rating = (TextView) rowView.findViewById(R.id.rating);

        txtTitle.setText(item[position].getName());
        price.setText("Price: " + item[position].getPrice());
        rating.setText("Rating: " + item[position].getRating());

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

    public Item[] sortbyprice(){
        boolean swapped = true;
        int j = 0;

        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < item.length - j; i++) {
                float price1 = Float.parseFloat(item[i].getPrice());
                float price2 = Float.parseFloat(item[i+1].getPrice());
                if (price1 > price2) {
                    Item temp = item[i];
                    item[i] = item[i + 1];
                    item[i + 1] = temp;
                    swapped = true;
                }
            }
        }
        return item;
    }

    public void clear(){
        item = null;
    }
    public void add(Item[] data){
        item = new Item[data.length];
        item = data;
    }
}
