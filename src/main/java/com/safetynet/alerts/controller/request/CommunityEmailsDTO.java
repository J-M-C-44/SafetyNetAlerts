package com.safetynet.alerts.controller.request;


import java.util.List;

public class CommunityEmailsDTO {
    private List<String> emails;

    public CommunityEmailsDTO(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        return "CommunityEmailsDTO{" +
                "emails=" + emails +
                '}';
    }

}
