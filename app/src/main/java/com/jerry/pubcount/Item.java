package com.jerry.pubcount;

import java.util.UUID;

public class Item {

    private String name;
    private int count;
    private float price;
    private UUID idNumber;


    public Item(String name, float price) {
        this.name = name;
        this.price = price;
        idNumber = UUID.randomUUID();
        count = 1;
    }

    public Item() {
        // TODO Auto-generated constructor stub
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public UUID getIdNumber() {
        return idNumber;
    }

    public void addCount() {
        count += 1;
    }

    public void removeCount() {
        count -= 1;
    }


}
