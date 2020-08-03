package com.example.foodpanda.Model;

public class CommentModel {
    public String itemName, itemComDate, itemComment;
    public int starScore;
    public boolean isTitle;

    public CommentModel(String itemName, String itemComDate, String itemComment, int starScore){
        this.itemName = itemName;
        this.itemComDate = itemComDate;
        this.itemComment = itemComment;
        this.starScore = starScore;
    }

    private void subComment(){

    }


    public void setLangLogo(String itemPicture){
        this.itemComment = itemComment;
    }

    public String getLangLogo(){
        return itemComment;
    }
}