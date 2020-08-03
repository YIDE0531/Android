package com.example.foodpanda.Model;

import java.io.Serializable;

public class EditModel implements Serializable {
    public String itemName;
    public int itemNum, itemSinglePrice, itemTotalPrice;

    public EditModel(String itemName, int itemNum, int itemSinglePrice, int itemTotalPrice){
        this.itemName = itemName;
        this.itemNum = itemNum;
        this.itemSinglePrice = itemSinglePrice;
        this.itemTotalPrice = itemTotalPrice;
    }
}