package com.example.hotelManageMent.hotel;

import com.example.hotelManageMent.amenity.Amenity;
import com.example.hotelManageMent.amenity.InRoomAmenity;
import com.example.hotelManageMent.exception.BookingException;
import com.example.hotelManageMent.guest.Guest;

import java.awt.print.Book;
import java.io.*;
import java.nio.Buffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Hotel {
    private String name;
    private HashMap<String, Guest> guests; // key: passport no, value: Guest object
    private ArrayList<Amenity> amenities;
    private HashMap<String,Booking> bookings; // key: booking ID, value: Booking object
    private HashMap<Date,ArrayList<Integer>> roomAvailability; // key: date, value: standard room count and deluxe room count
    /**
     * <dd-mon-yyyy>,<standard rooms available>,<deluxe rooms available>
     * {
     *     "2023-05-01": [10, 10]
     * }
     */
    public Hotel (String name, String roomFilename) throws IOException {
        this.name = name;
        setupGuest();
        setupAmenities();
        saveRoomAvailability();
        bookings = new HashMap<String, Booking>();
    }

    public Optional<Guest> searchGuest (String passport) {
        if (guests.containsKey(passport)) {
            return Optional.of(guests.get(passport));
        } else {
            return Optional.empty();
        }
    }

//    public static void main(String[] args) {
//        Hotel hotel = new Hotel("test", "testFileName");
//        hotel.guests = new HashMap<String,Guest>();
//        hotel.guests.put("nonExistPassPort", new Guest("passport", "name", "country"));
//        Optional<Guest> res = hotel.searchGuest("nonExistPassPort");
//        System.out.println(res);
//    }

    public boolean checkRoomAvailability(Date startDate, Date endDate) {
        if (endDate.compareTo(startDate) < 0) {
            return false;
        }
        ArrayList<Date> datesToCheck = getDatesBetweenStartAndEnd(startDate, endDate);
        for (Date currDate: datesToCheck) {
            if (!roomAvailability.containsKey(currDate)) {
                return false;
            }
        }
        return true;
    }

    public String listAmenity () {
        StringBuilder sb = new StringBuilder();
        if (amenities.size() == 0) {
            return "";
        }
        for (Amenity currAmenity: amenities) {
            sb.append(currAmenity + "\n");
        }
        return sb.toString();
    }

    public Optional<Amenity> getAmenity (String itemCode) {
        for (Amenity currAmenity: amenities) {
            String currItemCode = currAmenity.getItemCode();
            if (currItemCode == itemCode) {
                return Optional.of(currAmenity);
            }
        }
        return Optional.empty();
    }

    public Booking searchBooking(String bookingId) {
        if (bookings.containsKey(bookingId)) {
            return bookings.get(bookingId);
        }
        return null;
    }

    public ArrayList<Guest> searchBookingByPassport (String passport) {
        ArrayList<Guest> res = new ArrayList<Guest>();
        for (Map.Entry<String,Guest> currEntry: guests.entrySet()) {
            String currPassport = currEntry.getKey();
            Guest currGuest = currEntry.getValue();
            if (currPassport == passport) {
                res.add(currGuest);
            }
        }
        return res;
    }

    public void submitBooking (Booking newBooking) {
        try {
            if (!Objects.equals(newBooking.getStatus(), "Pending")) {
                throw new BookingException("Status Is Not Pending");
            }
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
        Date newBookingCheckInDate = newBooking.getCheckInDate();
        Date newBookingCheckOutDate = newBooking.getCheckOutDate();
        try {
            if (this.checkRoomAvailability(newBookingCheckInDate, newBookingCheckOutDate) == false) {
                throw new BookingException("No Available Room");
            }
        } catch (BookingException e) {
                System.out.println(e.getMessage());
        }
        // TODO: update room availability by deducting the room type count from check-in to check-out dates
        ArrayList<Date> datesToCheck = getDatesBetweenStartAndEnd(newBookingCheckInDate,newBookingCheckOutDate);
        for (Date currDate: datesToCheck) {
            ArrayList<Integer> standardDeluxeRoom = roomAvailability.get(currDate);
            Integer standardRoom = standardDeluxeRoom.get(0);
            Integer deluxeRoom = standardDeluxeRoom.get(1);
            String bookingRoom = newBooking.getRoomType();
            try {
                if (bookingRoom.equals("Standard")) {
                    if (standardRoom == 0) {
                        throw new BookingException("No Standard Room Available To Be Deducted");
                    } else {
                        standardRoom--;
                    }
                }
                if (bookingRoom.equals("Deluxe")) {
                    if (deluxeRoom == 0) {
                        throw new BookingException("No Deluxe Room Available To Be Deducted");
                    } else {
                        deluxeRoom--;
                    }
                }
            } catch (BookingException e) {
                System.out.println(e.getMessage());
            }
        }
        newBooking.setStatus("Confirmed");
        String newBookingId = newBooking.getBookingId();
        bookings.put(newBookingId, newBooking);
    }

    public void cancelBooking (String bookingId) {
        try {
            if (!bookings.containsKey(bookingId)) {
                throw new BookingException("No Such Booking To Cancel");
            }
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
        Booking toCancelBooking = bookings.get(bookingId);
        String toCancelBookingStatus = toCancelBooking.getStatus();
        try {
            if (!Objects.equals(toCancelBookingStatus, "Confirmed")) {
                throw new BookingException("Booking Cannot Be Cancelled");
            }
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
        toCancelBooking.setStatus("Cancelled");
        // TODO: update the room availability data (increment room type count by 1 fro check-in to check-out dates)
    }

    public void checkIn (String bookingId, String allocatedRoomNo) {
        try {
            boolean isBookingIdExist = bookings.containsKey(bookingId);
            if (isBookingIdExist) {
                // TODO: if booking is found, invoke the checkIn method of the Booking object
            } else {
                throw new BookingException("No Such Booking To Check In");
            }
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
    }



    public ArrayList<Date> getDatesBetweenStartAndEnd(Date startDate, Date endDate) {
        ArrayList<Date> datesInRange = new ArrayList<Date>();
        Calendar calendar = getCalendarWithoutTime(startDate);
        Calendar endCalendar = getCalendarWithoutTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            datesInRange.add(result);
            calendar.add(Calendar.DATE, 1);
        }

        return datesInRange;
    }

    private Calendar getCalendarWithoutTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public void setupGuest() throws IOException {
        // setup Guest objects into the dictionary by reading the information from "Guests.txt" and "Blacklist.txt"
        FileReader fileReader = new FileReader("guests.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.readLine();
        this.guests = new HashMap<>();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] splitLine = line.split(",");
            String guestPassport = splitLine[0];
            String guestName = splitLine[1];
            String guestCountry = splitLine[2];
            this.guests.put(guestPassport, new Guest(guestPassport, guestName, guestCountry));
//            System.out.println(Arrays.toString(splitLine));
        }
        System.out.println(this.guests);

    }

    public static void main(String[] args) throws IOException {
        Hotel hotel = new Hotel("testName", "testNo");
        hotel.saveRoomAvailability();
    }

    public void setupAmenities() throws IOException {
        FileReader fileReader = new FileReader("InRoomAmenity.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        bufferedReader.readLine();
        ArrayList<Amenity> amenities = new ArrayList<Amenity>();
        while (true) {
            // <itemCode>,<description>,<price>,<floorArea>
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            String[] splitLine = line.split(",");
            String itemCode = splitLine[0];
            String description = splitLine[1];
            String price = splitLine[2];
            Double doublePrice = Double.parseDouble(price);
            String floorArea = splitLine[3];
            Double doubleFloorArea = Double.parseDouble(floorArea);
            Amenity currAmenity = new InRoomAmenity(itemCode, description, doublePrice, doubleFloorArea);
            amenities.add(currAmenity);
        }

        FileReader fileReader2 = new FileReader("SharedAmenity.txt");
        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);
        bufferedReader2.readLine();
        while (true) {
            // <itemCode>,<description>,<price>
            String line2 = bufferedReader2.readLine();
            if (line2 == null) {
                break;
            }
            String[] splitLine2 = line2.split(",");
            String itemCode2 = splitLine2[0];
            String description2 = splitLine2[1];
            String price2 = splitLine2[2];
            Double doublePrice2 = Double.parseDouble(price2);
            Double doubleFloorArea2 = 0D;
            Amenity currAmenity = new InRoomAmenity(itemCode2, description2, doublePrice2, doubleFloorArea2);
            amenities.add(currAmenity);
        }
//        System.out.println(amenities);
    }

    public void saveRoomAvailability() throws IOException {
        FileReader fileReader3 = new FileReader("rooms_april2023.txt");
        BufferedReader bufferedReader3 = new BufferedReader(fileReader3);
        bufferedReader3.readLine();
        roomAvailability = new HashMap<Date,ArrayList<Integer>>();
        while (true) {
            // <itemCode>,<description>,<price>
            String line3 = bufferedReader3.readLine();
            if (line3 == null) {
                break;
            }
            // <dd-mon-yyyy>,<standard rooms available>,<deluxe rooms available>
            String[] splitLine3 = line3.split(",");
            String dateString = splitLine3[0];
            String standardRoomString = splitLine3[1];
            Integer standardRoom = Integer.parseInt(standardRoomString);
            String deluxeRoomString = splitLine3[2];
            Integer deluxeRoom = Integer.parseInt(deluxeRoomString);
            SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
            Date date;
            try {
                date = format.parse(dateString);
                ArrayList<Integer> standardDeluxeRoom = new ArrayList<Integer>();
                standardDeluxeRoom.add(standardRoom);
                standardDeluxeRoom.add(deluxeRoom);
                roomAvailability.put(date, standardDeluxeRoom);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(roomAvailability);
    }
}
