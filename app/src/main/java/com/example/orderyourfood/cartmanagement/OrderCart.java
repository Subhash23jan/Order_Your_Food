package com.example.orderyourfood.cartmanagement;

public class OrderCart {
    int image_id;
    String foodName;
    int price;
    int counts;

    public OrderCart(int image_id, String foodName, int price, int counts) {
        this.image_id = image_id;
        this.foodName = foodName;
        this.price = price;
        this.counts = counts;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }
}

