package com.example.hotelManageMent.amenity;

public abstract class Amenity {
    private String itemCode;
    private String description;
    private double price;

    public Amenity(String itemCode, String description, double price) {
        this.itemCode = itemCode;
        this.price = price;
        this.description = description;
    }

    public String getItemCode() {
        return itemCode;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    // abstract class can contain abstract method
    // however, abstract method not allowed to hve method body
    public abstract double getFloorArea();

    @Override
    public String toString() {
        return itemCode + ", " + description + ", " + price;
    }
}
