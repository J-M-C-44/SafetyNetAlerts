package com.safetynet.alerts.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    @Test
    void toString_shouldCreatePersonAndDisplayAttributes() {
        //arrange

        //act

        Person person = new Person("Harry","Covert","5, road to Nantes","Treillieres","12345","800-800-1234","harry.covert@gmail.com");
        String result= person.toString();
        String expected = "Person{firstName='Harry', lastName='Covert', address='5, road to Nantes', city='Treillieres', zip=12345, phone='800-800-1234', email='harry.covert@gmail.com'}";
        //assert
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void settersAndGuettersPerson_shouldCreateMedicalrecordAndSetAndGetAttributes() {
        //arrange

        //act
        Person person = new Person();
        person.setFirstName("Harry");
        person.setLastName("Covert");
        person.setAddress("5, road to Nantes");
        person.setCity("Treillieres");
        person.setZip("12345");
        person.setPhone("800-800-1234");
        person.setEmail("harry.covert@gmail.com");
        String resultFirstName = person.getFirstName();
        String resultLastName = person.getLastName();
        String resultAddress = person.getAddress();
        String resultCity = person.getCity();
        String resultZip = person.getZip();
        String resultPhone = person.getPhone();
        String resultEmail = person.getEmail();

        //assert
        assertThat(resultFirstName).isEqualTo("Harry");
        assertThat(resultLastName).isEqualTo("Covert");
        assertThat(resultAddress).isEqualTo("5, road to Nantes");
        assertThat(resultCity).isEqualTo("Treillieres");
        assertThat(resultZip).isEqualTo("12345");
        assertThat(resultPhone).isEqualTo("800-800-1234");
        assertThat(resultEmail).isEqualTo("harry.covert@gmail.com");
    }

}