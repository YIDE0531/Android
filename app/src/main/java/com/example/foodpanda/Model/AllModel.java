package com.example.foodpanda.Model;

public class AllModel {
    public String image, name;
    public int data;

    public AllModel(String friendname, String friendaccount) {
        this.image = friendname;
        this.name = friendaccount;
    }
    AllModel(String friendname, String friendaccount, int data) {
        this.image = friendname;
        this.name = friendaccount;
    }

}
