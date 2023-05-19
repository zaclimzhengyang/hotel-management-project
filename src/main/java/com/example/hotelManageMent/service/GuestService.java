package com.example.hotelManageMent.service;

import com.example.hotelManageMent.entity.GuestEntity;
import com.example.hotelManageMent.guest.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.hotelManageMent.repository.GuestRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class GuestService {
    @Autowired
    GuestRepository guestRepository;

    private final int numberOfThreads = 5;
    private ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

    public List<GuestEntity> getListOfGuests() {
        List<GuestEntity> guestList =  guestRepository.findAll();
        if (guestList == null) {
            System.out.println("guest list is null");
        } else if (guestList.size() == 0) {
            System.out.println("guest list is empty");
        } else {
            System.out.println(guestList);
        }
//        for (GuestEntity currentGuest: guestList) {
//            System.out.println(currentGuest.getPassport());
//        }
        return guestList;
    }

    public String getNameByPassportNo(String passportNo) {
        return guestRepository.getNameByPassport(passportNo);
    }

    // threadpool
    public List<String> getNameByPassportNos(ArrayList<String> passportNo) throws ExecutionException, InterruptedException {
        ArrayList<String> listOfNames = new ArrayList<String>();
        ArrayList<Future<String>> listOfRunningTasks = new ArrayList<Future<String>>();
        for (String currPassport: passportNo) {
            Callable<String> task = new Callable<String>() {
                @Override
                public String call() throws Exception {
                    String name = guestRepository.getNameByPassport(currPassport);
                    return name;
                }
            };
            Future<String> runningTask = executor.submit(task);
            listOfRunningTasks.add(runningTask);
        }
        /**
         * Is there a better way than the primitive while loop
         * to wait for the futures to be completed,
         *
         *
         */
        for (Future<String> future: listOfRunningTasks) {
            while (!future.isDone()) {
            }
            listOfNames.add(future.get());
        }

        return listOfNames;
    }

    // sequential
//    public List<String> getNamesByPassportNos(ArrayList<String> passportNo) {
//        ArrayList<String> listOfNames = new ArrayList<String>();
//        for (String currPassport: passportNo) {
//            String name = guestRepository.getNameByPassport(currPassport);
//            listOfNames.add(name);
//        }
//        return listOfNames;
//    }
}
