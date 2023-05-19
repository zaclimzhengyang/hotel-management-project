package com.example.hotelManageMent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="guest")
public class GuestEntity {
    @Id
    @Column(name="passport")
    private String passport;
    @Column(name="name")
    private String name;
    @Column(name="country")
    private String country;
    @Column(name="blacklist_reason")
    private String blackListReason;

    public String getPassport() {
        return passport;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }
}
