package com.example.hotelManageMent.amenity;

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

    public static void main(String[] args) throws IOException, Exception {
        Scanner scanner = null;
        ArrayList<Amenity> amenities = new ArrayList<Amenity>();
        double totalArea;
        double totalPrice;
        totalArea = 0;
        totalPrice = 0;

        FileReader fileReader = new FileReader("SharedAmenity.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.readLine();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] lineSplit = line.split(",");
            String itemCode = lineSplit[0];
            String description = lineSplit[1];
            double price = Double.parseDouble(lineSplit[2]);
            totalPrice += price;
            SharedAmenity currentAmenity = new SharedAmenity(itemCode, description, price);
            amenities.add(currentAmenity);
        }

        FileReader fileReader2 = new FileReader("inRoomAmenity.txt");
        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
        bufferedReader2.readLine();
        while (true) {
            bufferedReader2.readLine();
            String line2 = bufferedReader2.readLine();
            if (line2 == null) {
                break;
            }
            String[] lineSplit2 = line2.split(",");
            String itemCode2 = lineSplit2[0];
            String description2 = lineSplit2[1];
            double price2 = Double.parseDouble(lineSplit2[2]);
            double floorArea2 = Double.parseDouble(lineSplit2[3]);
            totalPrice += price2;
            totalArea += floorArea2;
            SharedAmenity currentAmenity = new SharedAmenity(itemCode2, description2, price2);
            amenities.add(currentAmenity);
        }

        for (Amenity currentAmenity : amenities) {
            System.out.println(currentAmenity);
        }

        System.out.println("Total floor area: " + totalArea);
        System.out.println("Total price: " + totalPrice);

    }
}
