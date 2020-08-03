package com.example.foodpanda.Model;

public class MenuModel{
    public String itemTitle, itemName, itemPrice, itemPicture;
    public int itemNum;
    public boolean isTitle;

    public MenuModel(String itemTitle, String itemName, String itemPrice, boolean isTitle, String langLogo, int itemNum){
        this.itemTitle = itemTitle;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.isTitle = isTitle;
        this.itemPicture = langLogo;
        this.itemNum = itemNum;
    }

    public void setLangLogo(String itemPicture){
        this.itemPicture = itemPicture;
    }

    public String getLangLogo(){
        return itemPicture;
    }
}