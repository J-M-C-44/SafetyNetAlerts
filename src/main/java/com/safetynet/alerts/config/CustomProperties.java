package com.safetynet.alerts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties (prefix = "com.safetynet.alerts")
public class CustomProperties {
    private String jsonfilepathname;

    public String getJsonfilepathname() {
        return jsonfilepathname;
    }

    public void setJsonfilepathname(String jsonfilepathname) {
        this.jsonfilepathname = jsonfilepathname;
    }
}
