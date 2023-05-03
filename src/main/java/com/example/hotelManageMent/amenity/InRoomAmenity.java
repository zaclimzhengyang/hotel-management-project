package com.example.hotelManageMent.amenity;

public class InRoomAmenity extends Amenity {
    double floorArea;

    public InRoomAmenity(String itemCode, String description, double price, double floorArea) {
        super(itemCode, description, price);
        this.floorArea = floorArea;
    }
    public double getFloorArea() {
        return floorArea;
    }

    @Override
    public String toString() {
        return super.toString() + ", floor area: " + floorArea + " sq ft";
    }
}
