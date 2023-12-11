package com.safetynet.alerts.controller.request;

import java.util.List;

public class PhonesAlertDTO {
    private List<String> phones;

    public PhonesAlertDTO(List<String> phones) {
        this.phones = phones;
    }

    public List<String> getPhones() {
        return phones;
    }

    @Override
    public String toString() {
        return "PhonesAlertDTO{" +
                "phones=" + phones +
                '}';
    }

}
