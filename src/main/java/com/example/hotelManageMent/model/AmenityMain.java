package com.example.hotelManageMent.model;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AmenityMain {

    public static List<String> convertResourceToList(Resource resource) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            return reader.lines().collect(Collectors.toList());
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = null;
        ArrayList<Amenity> amenities = new ArrayList<Amenity>();
        double totalArea;
        double totalPrice;
        try {
            totalArea = 0;
            totalPrice = 0;

            Resource resource = new ClassPathResource("SharedAmenity.txt");
            File file = resource.getFile();
            List<String> allLines = convertResourceToList(resource);
            for (int i = 1; i < 3; i++) {
                String[] firstLineValues = allLines.get(i).split(",");
                String itemCode = firstLineValues[0];
                String description = firstLineValues[1];
                double price = Double.parseDouble(firstLineValues[2]);
                totalPrice += price;
                SharedAmenity currentAmenity = new SharedAmenity(itemCode, description, price);
                amenities.add(currentAmenity);
            }

            Resource resource2 = new ClassPathResource("InRoomAmenity.txt");
            File file2 = resource2.getFile();
            List<String> allLines2 = convertResourceToList(resource2);
            for (int j = 1; j < 3; j++) {
                String[] firstLineValues = allLines2.get(j).split(",");
                String itemCode2 = firstLineValues[0];
                String description2 = firstLineValues[1];
                double price2 = Double.parseDouble(firstLineValues[2]);
                double floorArea2 = Double.parseDouble(firstLineValues[3]);
                totalPrice += price2;
                totalArea += floorArea2;
                SharedAmenity currentAmenity = new SharedAmenity(itemCode2, description2, price2);
                amenities.add(currentAmenity);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error while populating list of amenities");
            throw new RuntimeException(e);
        }

        for (Amenity currentAmenity : amenities) {
            System.out.println(currentAmenity);
        }

        System.out.println("Total floor area: " + totalArea);
        System.out.println("Total price: " + totalPrice);

    }
}
