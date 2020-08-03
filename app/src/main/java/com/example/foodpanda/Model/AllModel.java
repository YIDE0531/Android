package com.example.foodpanda.Model;

public class AllModel {
    public String image, name, infoUrl, title, titleNum, price, address, date, comment;
    public int data;

    public AllModel(String image, String name) {
        this.image = image;
        this.name = name;
    }
    public AllModel(String image, String name, String infoUrl) {
        this.image = image;
        this.name = name;
        this.infoUrl = infoUrl;
    }
    public AllModel(String title, String titleNum, String name, String image, String price, String address, String date, String comment) {
        this.title = title;
        this.titleNum = titleNum;
        this.name = name;
        this.image = image;
        this.price = price;
        this.address = address;
        this.date = date;
        this.comment = comment;
    }
}
