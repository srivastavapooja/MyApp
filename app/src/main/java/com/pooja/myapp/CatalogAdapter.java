package com.pooja.myapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.TextView;

/**
 * Created by Dumbledore on 10/21/16.
 */
public class CatalogAdapter extends BaseAdapter{

    private Context mContext ;
    private String[] mThumbIds;

    public CatalogAdapter(Context context){
        mContext = context;
    }
    public  CatalogAdapter(Context context, String[] data){
        mContext = context;
        mThumbIds = new String[data.length];
        mThumbIds = data;
    }
    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new TextView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(100,100));
            imageView.setBackgroundColor(imageView.getResources().getColor(R.color.colorBackground));
            //imageView.setScaleType(ImageView.ScaleType.CENTER);
            imageView.setPadding(8, 8, 8, 8);
            imageView.setGravity(0x11);
        } else {
            imageView = (TextView) convertView;
        }

        imageView.setHint(mThumbIds[position]);
        imageView.setHeight(300);

        imageView.setContentDescription(mThumbIds[position]);


        return imageView;

    }

    public void clear(){
        mThumbIds = null;
    }
    public void add(String[] string){
        mThumbIds = new String[string.length];
        mThumbIds = string;
    }


}
