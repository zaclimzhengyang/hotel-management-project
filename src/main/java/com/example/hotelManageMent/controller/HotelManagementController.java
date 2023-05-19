package com.example.hotelManageMent.controller;

import com.example.hotelManageMent.entity.GuestEntity;
import com.example.hotelManageMent.guest.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.hotelManageMent.service.GuestService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class HotelManagementController {
    @Autowired
    GuestService guestService;

    @GetMapping(path="/hello_world")
    public String helloWorld() {
        System.out.println("Hello world");
        return "Hello World";
    }

    @GetMapping(path="/getListOfGuests")
    public List<GuestEntity> getListOfGuests() {
        System.out.println("hit getListOfGuest");
        return guestService.getListOfGuests();
    }

    @GetMapping(path="/getNameByPassport/{passportNo}")
    public String getNameByPassport(@PathVariable String passportNo) {
        return guestService.getNameByPassportNo(passportNo);
    }

    @GetMapping(path="/getNamesByPassportNos")
    public List<String> getNamesByPassportNos(@RequestBody ArrayList<String> passportNos) throws ExecutionException, InterruptedException {
        /**
         * payload: ["passport no","passportno1"]
         *
         * return:
         * [
         *     "guest name",
         *     "guest name1"
         * ]
         */
        return guestService.getNameByPassportNos(passportNos);
    }
}
