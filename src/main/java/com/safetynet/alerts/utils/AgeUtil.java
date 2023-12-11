package com.safetynet.alerts.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public final class AgeUtil {
    public static final int MAJORITY_AGE = 18;

    private AgeUtil() {
    }

    public static Integer calculateAgeFromDate(String birthdate){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate formatedBirthdate = LocalDate.parse(birthdate, format);

        return Period.between(formatedBirthdate, LocalDate.now()).getYears();
    }

    public static Boolean isChild(String birthdate){
       if (birthdate == null || birthdate.isEmpty()) {
           return false;
       }
        return (calculateAgeFromDate(birthdate) <  MAJORITY_AGE);
    }
}
