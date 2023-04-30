package org.xsk.domain.account;

import org.xsk.domain.common.ValueObject;


public class PhysicalAddress extends ValueObject {
    String country;
    String city;
    String street;

    public PhysicalAddress(String country, String city, String street) {
        this.country = country;
        this.city = city;
        this.street = street;
    }
}
