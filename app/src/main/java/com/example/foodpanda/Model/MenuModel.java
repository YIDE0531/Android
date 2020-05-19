package com.example.foodpanda.Model;

public class MenuModel {
    public String itemTitle, itemName, itemPrice;
    public boolean isTitle;
    public int itemPicture;

    public MenuModel(String itemTitle, String itemName, String itemPrice, boolean isTitle, int langLogo){
        this.itemTitle = itemTitle;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.isTitle = isTitle;
        this.itemPicture = langLogo;
    }

    public void setLangLogo(int itemPicture){
        this.itemPicture = itemPicture;
    }

    public int getLangLogo(){
        return itemPicture;
    }
}