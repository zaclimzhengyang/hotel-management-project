package com.example.hotelManageMent.hotel;

import com.example.hotelManageMent.amenity.Amenity;
import com.example.hotelManageMent.guest.Guest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Hotel {
    private String name;
    private HashMap<String, Guest> guests;
    private ArrayList<Amenity> amenities;
    private HashMap<String, ArrayList<String>> bookings;
    private HashMap<Date,ArrayList<String>> roomAvailability;

    public Hotel (String name, String roomFilename) {
        this.name = name;
    }
}
