package com.safetynet.alerts.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class TransverseControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPersonsCoveredByStation_ShouldReturnPersonsCoveredByStationDTO() throws Exception {
        // act
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "99"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons[0].firstName").value("Harry"))
                .andExpect(jsonPath("$.persons[0].lastName").value("Covert"))
                .andExpect(jsonPath("$.persons[0].address").value("5, road to Nantes"))
                .andExpect(jsonPath("$.persons[0].phone").value("800-800-1234"))
                .andExpect(jsonPath("$.persons[1].firstName").value("Justine"))
                .andExpect(jsonPath("$.persons[1].lastName").value("Illusion"))
                .andExpect(jsonPath("$.persons[1].address").value("5, road to Nantes"))
                .andExpect(jsonPath("$.persons[1].phone").value("800-800-2345"))
                .andExpect(jsonPath("$.adultsCount").value(1))
                .andExpect(jsonPath("$.childrenCount").value(1));
    }

    @Test
    void getPersonsCoveredByStation_ShouldReturnNoContentWhenFirestationOrPersonNotFound() throws Exception {
        // act
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "unknown"))
        // assert
                .andExpect(status().isNoContent());
    }

    @Test
    void getChildrenAndHomeMembers_ShouldBeOKAndReturnChildAlertDTO() throws Exception {
        // act
        mockMvc.perform(get("/childAlert")
                        .param("address", "5, road to Nantes"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Justine"))
                .andExpect(jsonPath("$[0].lastName").value("Illusion"))
                .andExpect(jsonPath("$[0].age").value(12))
                .andExpect(jsonPath("$[0].homeMembers[0].firstName").value("Harry"))
                .andExpect(jsonPath("$[0].homeMembers[0].lastName").value("Covert"))
                .andExpect(jsonPath("$[0].homeMembers[0].address").value("5, road to Nantes"))
                .andExpect(jsonPath("$[0].homeMembers[0].phone").value("800-800-1234"));
    }

    @Test
    void getChildrenAndHomeMembers_ShouldReturnNoContent() throws Exception {
        // act
        mockMvc.perform(get("/childAlert")
                        .param("address", "unknown"))
        // assert
                .andExpect(status().isNoContent());
    }

    @Test
    void getPhonesByStation_ShouldBeOKAndReturnPhonesAlertDTO() throws Exception {
        // act
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "99"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phones[0]").value("800-800-1234"))
                .andExpect(jsonPath("$.phones[1]").value("800-800-2345"));
    }

    @Test
    void getPhonesByStation_ShouldReturnNoContent() throws Exception {
         // act
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "unknown"))
        // assert
                .andExpect(status().isNoContent());
    }

    @Test
    void getPersonsForFirebByAddress_ShouldBeOKAndReturnFireDTO() throws Exception {
        // act
        mockMvc.perform(get("/fire")
                        .param("address", "5, road to Nantes"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station").value("99"))
                .andExpect(jsonPath("$.persons[0].firstName").value("Harry"))
                .andExpect(jsonPath("$.persons[0].lastName").value("Covert"))
                .andExpect(jsonPath("$.persons[0].phone").value("800-800-1234"))
                .andExpect(jsonPath("$.persons[0].age").value("23"))
                .andExpect(jsonPath("$.persons[0].medications[0]").value("suppo:1u"))
                .andExpect(jsonPath("$.persons[0].medications[1]").value("paracetamol:500mg"))
                .andExpect(jsonPath("$.persons[0].allergies[0]").value("bouleau"))
                .andExpect(jsonPath("$.persons[1].firstName").value("Justine"))
                .andExpect(jsonPath("$.persons[1].lastName").value("Illusion"))
                .andExpect(jsonPath("$.persons[1].phone").value("800-800-2345"))
                .andExpect(jsonPath("$.persons[1].age").value("12"))
                .andExpect(jsonPath("$.persons[1].medications[0]").value("suppo:2u"))
                .andExpect(jsonPath("$.persons[1].medications[1]").value("paracetamol:1000mg"))
                .andExpect(jsonPath("$.persons[1].allergies[0]").value("testsUnitaires"));
    }

    @Test
    void getPersonsForFirebByAddress_ShouldReturnNoContent() throws Exception {
        // act
        mockMvc.perform(get("/fire")
                        .param("address", "unknown"))
        // assert
                .andExpect(status().isNoContent());
    }

    @Test
    void getPersonsForFloodByStations_ShouldBeOKAndReturnAddressWithCorrespondingPersonsAndMedicalData() throws Exception {
        // act
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "99"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].firstName").value("Harry"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].lastName").value("Covert"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].phone").value("800-800-1234"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].age").value("23"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].medications[0]").value("suppo:1u"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].medications[1]").value("paracetamol:500mg"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].allergies[0]").value("bouleau"));
    }

    @Test
    void getPersonsForFloodByStations_ShouldReturnNoContent() throws Exception {
        // act
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "33,44"))
        // assert
                .andExpect(status().isNoContent());
    }

    @Test
    void getPersonInfoByName_ShouldBeOKAndReturnPersonsInformations() throws Exception {
        // act
        mockMvc.perform(get("/personInfo")
                        .param("firstName", "Harry")
                        .param("lastName", "Covert"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName").value("Harry"))
                .andExpect(jsonPath("$.[0].lastName").value("Covert"))
                .andExpect(jsonPath("$.[0].email").value("harry.covert@gmail.com"))
                .andExpect(jsonPath("$.[0].age").value("23"))
                .andExpect(jsonPath("$.[0].medications[0]").value("suppo:1u"))
                .andExpect(jsonPath("$.[0].medications[1]").value("paracetamol:500mg"))
                .andExpect(jsonPath("$.[0].allergies[0]").value("bouleau"));
    }

    @Test
    void getPersonInfoByName_ShouldReturnNoContent() throws Exception {
        // act
        mockMvc.perform(get("/personInfo")
                        .param("firstName", "unknown")
                        .param("lastName", "unknown"))
        // assert
                .andExpect(status().isNoContent());
    }

    @Test
    void getCommunityEmail_ShouldBeOKAndReturnEmails() throws Exception {
        // act
        mockMvc.perform(get("/communityEmail")
                        .param("city", "Treillieres"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emails[0]").value("harry.covert@gmail.com"))
                .andExpect(jsonPath("$.emails[1]").value("justine.illusion@gmail.com"));
    }

    @Test
    void getCommunityEmail_ShouldReturnNoContent() throws Exception {
        // act
        mockMvc.perform(get("/communityEmail")
                        .param("city", "unknown"))
        // assert
                .andExpect(status().isNoContent());
    }
}