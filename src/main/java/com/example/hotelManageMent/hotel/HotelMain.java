package com.example.hotelManageMent.hotel;

import java.io.*;
import java.util.Arrays;

public class HotelMain {

    public static void main(String[] args) throws IOException {
        FileReader fileReader = new FileReader("rooms_april2023.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.readLine();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] splitLine = line.split(",");
            System.out.println(Arrays.toString(splitLine));
        }
    }
}
