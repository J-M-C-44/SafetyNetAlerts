package com.safetynet.alerts.controller.request.person;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class PersonWithoutNameDTO {
    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    @Pattern(regexp = "[0-9]{1,5}")
    private String zip;

    @NotBlank
    @Pattern(regexp = "^[0-9]{3}-[0-9]{3}-[0-9]{4}$")
    private String phone;

    @NotBlank
    @Email
    private String email;

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "PersonWithoutNameDTO{" +
                "address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
