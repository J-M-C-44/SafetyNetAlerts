package com.safetynet.alerts.controller.request.tranverse;

import java.util.List;

public class PhonesAlertDTO {
    private final List<String> phones;

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
