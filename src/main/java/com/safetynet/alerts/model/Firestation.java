package com.safetynet.alerts.model;

public class Firestation {
    private String address;
    private Integer station;

    public Firestation(String address, Integer station) {
        this.address = address;
        this.station = station;
    }
    public Firestation() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }
}
