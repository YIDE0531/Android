package com.example.foodpanda.Model;

public class PIModel {
    Integer langLogo;
    String shopName, langLogoUrl;

    public PIModel(Integer langLogo){
        this.langLogo = langLogo;
    }

    public PIModel(String langLogo, String shopName){
        this.langLogoUrl = langLogo;
        this.shopName = shopName;
    }

    public Integer getLangLogo(){
        return langLogo;
    }

    public String getlangLogoUrl(){
        return langLogoUrl;
    }

    public String getShopName(){
        return shopName;
    }
}
