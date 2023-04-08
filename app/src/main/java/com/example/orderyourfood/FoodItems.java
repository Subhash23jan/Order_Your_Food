package com.example.orderyourfood;

public class FoodItems {
    int image_id;
    String name;
    int price;

    public FoodItems(int image_id, String name, int price) {
        this.image_id = image_id;
        this.name = name;
        this.price = price;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

