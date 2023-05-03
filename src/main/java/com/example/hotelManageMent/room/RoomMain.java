package com.example.hotelManageMent.room;

import com.example.hotelManageMent.exception.BookingException;
import com.example.hotelManageMent.exception.MinFloorAreaException;
import com.example.hotelManageMent.amenity.InRoomAmenity;
import com.example.hotelManageMent.amenity.SharedAmenity;
import com.example.hotelManageMent.hotel.Bed;

public class RoomMain {

    public static void main(String[] args) throws MinFloorAreaException, BookingException {
        Bed newBed1 = new Bed("Super Single", 10, "Super Single Bed");
        Room newRoom1 = new Room("Deluxe", 16.99, newBed1);
        InRoomAmenity newAmenity11 = new InRoomAmenity("FRIDGE", "Mini Fridge (50L)", 4.59, 0.25);
        InRoomAmenity newAmenity12 = new InRoomAmenity("CHAIR", "Foldable Chair (42cm x 38cm)",2.59,0.16);
        SharedAmenity newAmenity13 = new SharedAmenity("WI-FI","One-day Wi-Fi access",1.00);
        SharedAmenity newAmenity14 = new SharedAmenity("GYM-PEP", "Per entry pass to gym (Level 4-01)", 1.00);
        newRoom1.addAmenity(newAmenity11);
        newRoom1.addAmenity(newAmenity12);
        newRoom1.addAmenity(newAmenity13);
        newRoom1.addAmenity(newAmenity14);
        System.out.println("newRoom1 before removing: ");
        System.out.println(newRoom1.toString());

        newRoom1.removeAmenity("FRIDGE");
        newRoom1.removeAmenity("GYM-PEP");

        System.out.println("\n");
        System.out.println("newRoom1 after removing: ");
        System.out.println(newRoom1.toString());


    }
}
