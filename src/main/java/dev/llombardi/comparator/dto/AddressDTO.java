package dev.llombardi.comparator.dto;

import dev.llombardi.comparator.annotation.DisplayName;
import dev.llombardi.comparator.annotation.DtoObserver;

@DtoObserver
public class AddressDTO {
    @DisplayName("Street")
    private String street;
    
    @DisplayName("City")
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}