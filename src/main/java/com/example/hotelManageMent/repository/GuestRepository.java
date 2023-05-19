package com.example.hotelManageMent.repository;

import com.example.hotelManageMent.entity.GuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<GuestEntity,String> {
    //    @Query("SELECT g FROM GuestEntity g")
    @Query(value = "SELECT * FROM guest", nativeQuery = true)
    public List<GuestEntity> getAllGuest();

    @Query(value = "SELECT g.name FROM guest g WHERE g.passport = ?1", nativeQuery = true)
    public String getNameByPassport(String passportNo);
}
