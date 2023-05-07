package com.example.hotelManageMent.hotel;

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
        Double floorArea = typeSize.get(type);
        if (floorArea == null) {
            return 0;
        } else {
            return floorArea;
        }
    }

    @Override
    public String toString() {
        return description + ", " + price;
    }
}
