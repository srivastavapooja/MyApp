package com.pooja.myapp;

import android.util.Log;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Created by Dumbledore on 10/27/16.
 */
public class ProductDetail {
    private static  String name;
    private static  String imageurl;
    private static  String price;
    private static  String rating;
    private  String[] reviews;

    ProductDetail(String name, String imageurl, String price, String rating, String[] reviews){
        this.name = name;
        this.imageurl = imageurl;
        this.price = price;
        this.rating = rating;
       // this.reviews = new String[reviews.length];
        this.reviews = Arrays.copyOf(reviews, reviews.length);

    }

    public ProductDetail(ProductDetail detail) {
        name = detail.getName();
        price = detail.getPrice();
        imageurl = detail.getImageurl();
        rating = detail.getRating();

    }

    public String getName(){
        return name;
    }
    public String getImageurl(){
        return imageurl;
    }
    public String getPrice(){
        return price;
    }
    public String getRating(){
        return rating;
    }
    public String[] getReviews(){
        return reviews;
    }

    public String getReview(int index){

        return reviews[index];
    }
    public String print(){
        String printstr = name+price+rating+reviews[0]+imageurl;
        return printstr;
    }

}
