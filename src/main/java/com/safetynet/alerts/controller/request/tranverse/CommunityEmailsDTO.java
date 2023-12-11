package com.safetynet.alerts.controller.request.tranverse;


import java.util.List;

public class CommunityEmailsDTO {
    private final List<String> emails;

    public CommunityEmailsDTO(List<String> emails) {
        this.emails = emails;
    }

    public List<String> getEmails() {
        return emails;
    }

    @Override
    public String toString() {
        return "CommunityEmailsDTO{" +
                "emails=" + emails +
                '}';
    }

}
