package com.safetynet.alerts.controller.request.firestation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class FirestationWithoutAddressDTO {

    @NotBlank
    @Pattern(regexp = "[0-9]{1,999999999}")
    private String station;

    public String getStation() {
        return station;
    }

    @Override
    public String toString() {
        return "FirestationWithoutAddressDTO{" +
                ", station=" + station +
                '}';
    }
}
