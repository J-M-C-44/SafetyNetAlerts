package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.request.medicalrecord.MedicalRecordDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MedicalRecordControllerIT {
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper jsonMapper = new ObjectMapper();
    private final MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO("Harry","Covert","12/31/2000", List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    private final MedicalRecordDTO medicalRecordDTO2 = new MedicalRecordDTO("Harry","Corouge","12/31/2000", List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));

    @Test
    void getMedicalRecord_ShouldBeOKAndReturnMedicalRecordDTO() throws Exception {
        // act
        mockMvc.perform(get("/medicalRecord")
                        .param("firstName", "Harry")
                        .param("lastName", "Covert"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Harry"))
                .andExpect(jsonPath("$.lastName").value("Covert"))
                .andExpect(jsonPath("$.birthdate").value("12/31/2000"))
                .andExpect(jsonPath("$.medications[0]").value("suppo:1u"))
                .andExpect(jsonPath("$.medications[1]").value("paracetamol:500mg"))
                .andExpect(jsonPath("$.allergies[0]").value("bouleau"));
    }

    @Test
    void getMedicalRecord_ShouldReturnNoContentWhenMedicalRecordNotFound() throws Exception {
         // act
        mockMvc.perform(get("/medicalRecord")
                        .param("firstName", "Unknown")
                        .param("lastName", "Unknown"))
        // assert
                .andExpect(status().isNoContent());
    }

    @Order(1)
    @Test
    void createMedicalRecord_ShouldBeOKAndReturnMedicalRecordDTO()throws Exception {
        // arrange
        String inputJson = jsonMapper.writeValueAsString(medicalRecordDTO2);

        // act
        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Harry"))
                .andExpect(jsonPath("$.lastName").value("Corouge"))
                .andExpect(jsonPath("$.birthdate").value("12/31/2000"))
                .andExpect(jsonPath("$.medications[0]").value("suppo:1u"))
                .andExpect(jsonPath("$.medications[1]").value("paracetamol:500mg"))
                .andExpect(jsonPath("$.allergies[0]").value("bouleau"));
    }

    @Test
    void createMedicalRecord_ShouldReturnBadRequestWhenAlreadyExist()throws Exception {
        // arrange
        String inputJson = jsonMapper.writeValueAsString(medicalRecordDTO1);

        // act
        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isBadRequest());
    }

    @Order(2)
    @Test
    void updateMedicalRecord_ShouldBeOKAndReturnMedicalRecordDTO() throws Exception {
        // arrange
        String inputJson = "{\"birthdate\": \"12/31/2000\" , \"medications\": [\"suppo:10u\", \"paracetamol:500mg\"] , \"allergies\": [\"bouleau\"]}";

        // act
        mockMvc.perform(put("/medicalRecord/Harry/Corouge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Harry"))
                .andExpect(jsonPath("$.lastName").value("Corouge"))
                .andExpect(jsonPath("$.birthdate").value("12/31/2000"))
                .andExpect(jsonPath("$.medications[0]").value("suppo:10u"))
                .andExpect(jsonPath("$.medications[1]").value("paracetamol:500mg"))
                .andExpect(jsonPath("$.allergies[0]").value("bouleau"));
    }

    @Test
    void updateMedicalRecord_ShouldReturnNotFound() throws Exception {
        // arrange
        String inputJson = "{\"birthdate\": \"12/31/2000\" , \"medications\": [\"suppo:10u\", \"paracetamol:500mg\"] , \"allergies\": [\"bouleau\"]}";

        // act
        mockMvc.perform(put("/medicalRecord/Unknown/Unknown")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isNotFound());
    }

    @Order(3)
    @Test
    void deleteMedicalRecord_ShouldBeOK() throws Exception {

        mockMvc.perform(delete("/medicalRecord/Harry/Corouge"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMedicalRecord_ShouldReturnNotFound() throws Exception {

        mockMvc.perform(delete("/medicalRecord/Unknown/Unknown"))
                .andExpect(status().isNotFound());
    }

}
