package com.example.hotelManageMent.model;

import com.example.hotelManageMent.exception.BookingException;
import com.example.hotelManageMent.room.Room;

import java.util.Date;

public class Booking {
    private static int nextId = 1;
    private String bookingId;
    private Guest guest;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;
    private String allocatedRoomNo;
    private String status;

    public Booking (Guest guest, Room room, Date checkInDate, Date checkOutDate) {
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.allocatedRoomNo = null;
        this.status = "Pending";
        try {
            if (guest.isBlackListed()) {
                throw new BookingException("Guest Is Blacklisted");
            }
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (checkOutDate.compareTo(checkInDate) <= 1) {
                throw new BookingException("Check Out Date Is Not At Least 1 Day After Check In Date");
            }
        } catch (BookingException e) {
                System.out.println(e.getMessage());
            }
    }

    public String getBookingId() {
        return bookingId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public String getStatus() {
        return status;
    }

    public String getPassport() {
        return guest.getPassport();
    }

    public String getRoomType() {
        return room.getType();
    }

    public double getTotalPrice() {
        return room.getFullPrice();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void checkIn(String allocatedRoomNo) {
        try {
            if (this.getStatus() != "Confirmed") {
                throw new BookingException("Status Is Not Confirmed");
            }
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
        try {
            Date currentDate = new Date();
            if (currentDate.compareTo(checkInDate) != 0) {
                throw new BookingException("Check In Not done On Check In Date Itself");
            }
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
        try {
            if (guest.isBlackListed()) {
                throw new BookingException("Guest Is Blacklisted");
            }
        } catch (BookingException e) {
            System.out.println(e.getMessage());
        }
        setStatus("Checked-In");
        this.allocatedRoomNo = allocatedRoomNo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Booking ID: " + getBookingId());
        sb.append("\nPassport Number: " + getPassport());
        sb.append("\nName: " + guest.getName());
        sb.append("\nCheck-In/Out dates: " + getCheckInDate() + " / " + getCheckOutDate());
        sb.append("\nBooking Status: " + getStatus());
        sb.append("\n" + room.toString());
        sb.append("\nTotal Price: " + room.getFullPrice());

        return sb.toString();
    }
}
