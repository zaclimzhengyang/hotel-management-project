package com.example.hotelManageMent.room;

import com.example.hotelManageMent.exception.BookingException;
import com.example.hotelManageMent.exception.MinFloorAreaException;
import com.example.hotelManageMent.amenity.Amenity;
import com.example.hotelManageMent.hotel.Bed;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private static final double MIN_EXIT_SPACE = 1.84;
    private static HashMap<String,Double> typeSize = new HashMap<String,Double>() {
            {
                    put("Standard", 4.2);
                    put("Deluxe", 4.83);
            }
    };

    private String type;
    private double roomPrice;
    private Bed bed;
    private ArrayList<Amenity> amenities;

    public Room (String type, double roomPrice, Bed bed) {
        this.type = type;
        this.roomPrice = roomPrice;
        this.bed = bed;
        this.amenities = new ArrayList<Amenity>();
    }

    public String getType() {
        return type;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public double getFullPrice() {
        double amenitiesPrice = 0;
        for (Amenity currAmenity: amenities) {
            amenitiesPrice += currAmenity.getPrice();
        }
        return roomPrice + amenitiesPrice + bed.getPrice();
    }

    public void addAmenity(Amenity newItem) throws MinFloorAreaException, BookingException {
        try {
            if (amenities.contains(newItem)) {
                throw new BookingException("Amenity Already Exist In Amenities List");
            }

            double currRoomFloorArea = typeSize.get(type);
//            System.out.println("currFloorArea at the start: " + typeSize.get(type));
            amenities.add(newItem);
            for (Amenity currAmenity: amenities) {
                double currFloorArea = currAmenity.getFloorArea();
//                System.out.println("currFloorArea: " + currFloorArea);
                currRoomFloorArea -= currAmenity.getFloorArea();
//                System.out.println("currRoomFloorArea after deducting currFloorArea: " + currRoomFloorArea);
            }
            if (currRoomFloorArea < MIN_EXIT_SPACE) {
                throw new MinFloorAreaException("Floor Area Below Minimum of 1.84m2");
            }

        } catch (MinFloorAreaException e) {
            System.out.println(e.getMessage());
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }

//        if (newItem.getFloorArea() < MIN_EXIT_SPACE) {
//            throw new MinFloorAreaException("Floor Area Below Minimum of 1.84m2");
//        }
//        if (amenities.contains(newItem)) {
//            throw new BookingException("Amenity Already Exist In Amenities List");
//        }
//        amenities.add(newItem);
    }

    public void removeAmenity(String itemCode) {
        try {
            for (Amenity currAmenity: amenities) {
                if (currAmenity.getItemCode() == itemCode) {
                    amenities.remove(currAmenity);
                    return;
                }
            }
            throw new BookingException("No Such Amenity");
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }

    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(type + ", $" + getRoomPrice());
        if (amenities.size() == 0) {
            sb.append("\nAmenities List Is Empty");
        }
        for (Amenity currAmenity: amenities) {
            sb.append("\n" + currAmenity.getDescription() + ", $" + currAmenity.getPrice());
        }
        sb.append("\nFull Price: $" + getFullPrice());
        return sb.toString();
    }
}
