package com.example.hotelManageMent.guest;

import java.util.ArrayList;

public class Guest {
    private String passport;
    private String name;
    private String country;
    private ArrayList<ArrayList<String>> blacklistedReason;

    public Guest(String passport, String name, String country) {
        this.passport = passport;
        this.name = name;
        this.country = country;
        this.blacklistedReason = new ArrayList<>();
    }

    public String getPassport() {
        return passport;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public boolean isBlackListed() {
        // return true if black list is not empty
        // return false if black list is empty
        return !blacklistedReason.isEmpty();
    }

    public void blackList (String dateReported, String reason) {
        ArrayList<String> offence = new ArrayList<>();
        offence.add(dateReported);
        offence.add(reason);
        blacklistedReason.add(offence);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Passport: " + passport);
        sb.append("\nName: " + name);
        sb.append("\nCountry: " + country);
        if (isBlackListed()) {
            sb.append("<< Blacklisted on date, reason >>");
            for (ArrayList<String> offence: blacklistedReason) {
                sb.append("\n" + offence.get(0));
                sb.append("   " + offence.get(1));
            }
        } else {
            sb.append("\nNo blacklist record");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        Guest testGuest = new Guest("K1234567G", "Kyrie Irving", "USA");
        testGuest.blackList("12-Feb-2023","Drunk and disorderly");
        testGuest.blackList("12-Feb-2024", "Assault employees");
        System.out.println(testGuest.toString());
    }
}
