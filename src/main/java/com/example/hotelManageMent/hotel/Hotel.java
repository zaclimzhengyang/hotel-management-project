package com.example.hotelManageMent.hotel;

import com.example.hotelManageMent.amenity.Amenity;
import com.example.hotelManageMent.exception.BookingException;
import com.example.hotelManageMent.guest.Guest;

import java.awt.print.Book;
import java.util.*;

public class Hotel {
    private String name;
    private HashMap<String, Guest> guests; // key: passport no, value: Guest object
    private ArrayList<Amenity> amenities;
    private HashMap<String,Booking> bookings; // key: booking ID, value: Booking object
    private HashMap<Date,ArrayList<String>> roomAvailability; // key: date, value: standard room count and deluxe room count

    public Hotel (String name, String roomFilename) {
        this.name = name;
        setupGuest();
        setupAmenities();
        saveRoomAvailability();
        bookings = new HashMap<String, Booking>();
    }

    public Guest searchGuest (String passport) {
        if (guests.containsKey(passport)) {
            return guests.get(passport);
        } else {
            return null;
        }
    }

    public boolean checkRoomAvailability(Date startDate, Date endDate) {
        if (endDate.compareTo(startDate) < 0) {
            return false;
        }
        // TODO: check start or end dates are not in the roomAvailability dictionary
        ArrayList<Date> datesToCheck = getDatesBetweenStartAndEnd(startDate, endDate);
        for (Date currDate: datesToCheck) {
            if (!roomAvailability.containsKey(currDate)) {
                return false;
            }
        }
        return true;
    }

    public String listAmeinity () {
        StringBuilder sb = new StringBuilder();
        if (amenities.size() == 0) {
            return "";
        }
        for (Amenity currAmenity: amenities) {
            sb.append(currAmenity + "\n");
        }
        return sb.toString();
    }

    public Amenity getAmenity (String itemCode) {
        for (Amenity currAmenity: amenities) {
            String currItemCode = currAmenity.getItemCode();
            if (currItemCode == itemCode) {
                return currAmenity;
            }
        }
        return null;
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
            if (newBooking.getStatus() != "Pending") {
                throw new BookingException("Status Is Not Pending");
            }
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
        // TODO: check there should be check-in to check-out date, for the room type
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
            if (toCancelBookingStatus != "Confirmed") {
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

    public void setupGuest() {
        // setup Guest objects into the dictionary by reading the information from "Guests.txt" and "Blacklist.txt"
    }

    public void setupAmenities() {
        // setup the current set of Amenity objects into a list using the information provided in Appendix A1 and A2
    }

    public void saveRoomAvailability() {
        // with the roomFilename, setup the room availability as shown in appendix c
    }
}
