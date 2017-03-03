package com.pooja.myapp;

/**
 * Created by Dumbledore on 10/26/16.
 */
public class Item {
    private final String name;
    private final String price;
    private final String rating;
    private final String imagesrc;

    public Item(String name, String price, String rating, String img) {
        this.name = name;
        this.price = price;
        this.rating = rating;
        this.imagesrc = img;
    }
    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public String getRating(){
        return rating;
    }

    public String getImagesrc(){
        return imagesrc;
    }
    public String print(){
        String item = "Name = "+name+ "Price = "+price+"Rating = "+rating+"img = "+imagesrc;
        return item;
    }
}
