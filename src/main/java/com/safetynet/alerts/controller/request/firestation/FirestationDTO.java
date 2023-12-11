package com.safetynet.alerts.controller.request.firestation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class FirestationDTO {
    @NotBlank
    private final String address;
    @NotBlank
    @Pattern(regexp = "[0-9]{1,999999999}")
    private final String station;

    public FirestationDTO(String address, String station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public String getStation() {
        return station;
    }

    @Override
    public String toString() {
        return "FirestationDTO{" +
                "address='" + address + '\'' +
                ", station=" + station +
                '}';
    }
}
