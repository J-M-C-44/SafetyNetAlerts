package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.request.firestation.FirestationDTO;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FirestationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper jsonMapper = new ObjectMapper();
    private final FirestationDTO firestationDTO1 = new FirestationDTO("5, road to Nantes",  "99");
    private final FirestationDTO firestationDTO2 = new FirestationDTO("5, road to Rennes",  "77");
    private final FirestationDTO firestationDTO3 = new FirestationDTO("5, road to Brest",  "55");

    @Order(1)
    @Test
    void createFirestation_ShouldBeOKAndReturnFirestationDTO2() throws Exception {
        // arrange
        String inputJson = jsonMapper.writeValueAsString(firestationDTO2);

        // act
        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("5, road to Rennes"))
                .andExpect(jsonPath("$.station").value("77"));
    }
    @Order(2)
    @Test
    void createFirestation_ShouldBeOKAndReturnFirestationDTO3() throws Exception {
        // arrange
        String inputJson = jsonMapper.writeValueAsString(firestationDTO3);

        // act
        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("5, road to Brest"))
                .andExpect(jsonPath("$.station").value("55"));
    }

    @Test
    void createFirestation_ShouldReturnBadRequestWhenAlreadyExist() throws Exception {
        // arrange
        String inputJson = jsonMapper.writeValueAsString(firestationDTO1);

        // act
        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isBadRequest());
    }
    @Order(3)
    @Test
    void updateFirestation_ShouldBeOKAndReturnFirestationDTO() throws Exception{
        String inputJson = "{\"station\": \"88\"}";

        // act
        mockMvc.perform(put("/firestation/5, road to Rennes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("5, road to Rennes"))
                .andExpect(jsonPath("$.station").value("88"));
    }

    @Test
    void updateFirestation_ShouldReturnNotFound() throws Exception{
        String inputJson = "{\"station\": \"66\"}";

        // act
        mockMvc.perform(put("/firestation/5, road to Nowhere")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isNotFound());
    }
    @Order(4)
    @Test
    void deleteFirestationByAddress_ShouldBeOK() throws Exception {

        mockMvc.perform(delete("/firestation/5, road to Rennes"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFirestationByAddress_ShouldReturnNotFound() throws Exception {

        mockMvc.perform(delete("/firestation/5, road to Nowhere"))
                .andExpect(status().isNotFound());
    }
    @Order(5)
    @Test
    void deleteFirestationByStation_ShouldBeOK() throws Exception {

        mockMvc.perform(delete("/firestation")
                        .param("station", "55"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteFirestationByStation_ShouldReturnNotFound() throws Exception {

        mockMvc.perform(delete("/firestation")
                        .param("station", "66"))
                .andExpect(status().isNotFound());
    }
}