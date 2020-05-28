package com.example.foodpanda.Model;

public class PIModel {
    Integer langLogo;
    String shopName, image, infoUrl;

    public PIModel(Integer langLogo){
        this.langLogo = langLogo;
    }

    public PIModel(String image, String shopName, String infoUrl){
        this.image = image;
        this.shopName = shopName;
        this.infoUrl = infoUrl;
    }

    public Integer getLangLogo(){
        return langLogo;
    }

    public void setlangLogoUrl(String image){
        this.image = image;
    }

    public String getlangLogoUrl(){
        return image;
    }

    public String getShopName(){
        return shopName;
    }

    public String getInfoUrl(){
        return infoUrl;
    }
}
