package com.example.hotelManageMent.model;

public class SharedAmenity extends Amenity{

    public SharedAmenity(String itemCode, String description, double price) {
        super(itemCode, description, price);
    }

    public double getFloorArea() {
        /**
         * returns 0, as these amenities are mostly in shared / commonn area,
         * or not occupying any floor area of the room e.g. gym, swimming pool, wifi
         */
        return 0F;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
