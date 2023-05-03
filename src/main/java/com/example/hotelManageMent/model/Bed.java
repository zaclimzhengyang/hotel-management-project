package com.example.hotelManageMent.model;

import java.util.HashMap;

public class Bed {
    private static HashMap<String,Double> typeSize = new HashMap<String,Double>()
    {
        {
            put("Single", 1.73);
            put("Super", 2.03);
        }

    };
    private String type;
    private double price;
    private String description;

    public Bed (String type, double price, String description) {
        this.type = type;
        this.price = price;
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public double getFloorArea(String type) {
        if (typeSize.get(type) == null) {
            return 0;
        } else {
            return typeSize.get(type);
        }
    }

    @Override
    public String toString() {
        return description + ", " + price;
    }
}
