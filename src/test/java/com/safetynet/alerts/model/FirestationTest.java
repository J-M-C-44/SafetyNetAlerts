package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FirestationTest {

    @Test
    void toStringFirestation_shouldCreateFirestationAndDisplayAttributes() {
        //arrange

        //act
        Firestation firestation = new Firestation("5, road to Nantes", "99");
        String result= firestation.toString();
        String expected = "Firestation{address='5, road to Nantes', station=99}";

        //assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void settersAndGuettersFirestation_shouldCreateFiretsationAndSetAndGetAttributes() {
        //arrange
        String expectedAddress = "5, road to Nantes";
        String expectedStation = "99";

        //act
        Firestation firestation = new Firestation();
        firestation.setAddress("5, road to Nantes");
        firestation.setStation("99");
        String resultAddress = firestation.getAddress();
        String resultStation = firestation.getStation();

        //assert
        assertThat(resultAddress).isEqualTo(expectedAddress);
        assertThat(resultStation).isEqualTo(expectedStation);

    }
}