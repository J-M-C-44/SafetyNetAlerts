package com.safetynet.alerts.controller.request;

import jakarta.validation.constraints.*;

public class PersonDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
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

    public PersonDTO(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

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
}


