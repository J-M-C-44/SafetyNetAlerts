package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.request.person.PersonDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerIT {
    @Autowired
    private MockMvc mockMvc;

    ObjectMapper jsonMapper = new ObjectMapper();
    private final PersonDTO personDTO1 = new PersonDTO("Harry","Covert","5, road to Nantes","Treillieres","12345","800-800-1234","harry.covert@gmail.com");
    private final PersonDTO personDTO2 = new PersonDTO("Harry","Corouge","5, road to Nantes","Treillieres","12345","800-800-4567","harry.corouge@gmail.com");

    @Test
    void getPerson_ShouldBeOKAndReturnPersonDTO() throws Exception {

         // act
        mockMvc.perform(get("/person")
                        .param("firstName", "Harry")
                        .param("lastName", "Covert"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Harry"))
                .andExpect(jsonPath("$.lastName").value("Covert"))
                .andExpect(jsonPath("$.address").value("5, road to Nantes"))
                .andExpect(jsonPath("$.city").value("Treillieres"))
                .andExpect(jsonPath("$.zip").value("12345"))
                .andExpect(jsonPath("$.phone").value("800-800-1234"))
                .andExpect(jsonPath("$.email").value("harry.covert@gmail.com"));

    }

    @Test
    void getPerson_ShouldReturnNoContentWhenPersonNotFound() throws Exception {

        // act
        mockMvc.perform(get("/person")
                        .param("firstName", "unknown")
                        .param("lastName", "unknown"))
        // assert
                .andExpect(status().isNoContent());
    }

    @Test
    void createPerson_ShouldReturnBadRequestWhenAlreadyExist() throws Exception {
        // arrange
        String inputJson = jsonMapper.writeValueAsString(personDTO1);
        // act
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isBadRequest());
    }

    @Order(1)
    @Test
    void createPerson_ShouldBeOKAndReturnPersonDTO() throws Exception {
        // arrange
        String inputJson = jsonMapper.writeValueAsString(personDTO2);
        // act
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Harry"))
                .andExpect(jsonPath("$.lastName").value("Corouge"))
                .andExpect(jsonPath("$.address").value("5, road to Nantes"))
                .andExpect(jsonPath("$.city").value("Treillieres"))
                .andExpect(jsonPath("$.zip").value("12345"))
                .andExpect(jsonPath("$.phone").value("800-800-4567"))
                .andExpect(jsonPath("$.email").value("harry.corouge@gmail.com"));
    }

    @Order(2)
    @Test
    void updatePerson_ShouldBeOKAndReturnPersonDTO() throws Exception {
        // arrange
        String inputJson = "{\"address\": \"5, road to Nantes\" , \"city\": \"Treillieres\" , \"zip\": \"12345\" , \"phone\": \"800-800-6789\" , \"email\": \"harry.corouge@gmail.com\"}";

        // act
        mockMvc.perform(put("/person/Harry/Corouge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Harry"))
                .andExpect(jsonPath("$.lastName").value("Corouge"))
                .andExpect(jsonPath("$.city").value("Treillieres"))
                .andExpect(jsonPath("$.address").value("5, road to Nantes"))
                .andExpect(jsonPath("$.zip").value("12345"))
                .andExpect(jsonPath("$.phone").value("800-800-6789"))
                .andExpect(jsonPath("$.email").value("harry.corouge@gmail.com"));

    }
    @Test
    void updatePerson_ShouldReturnNotFound() throws Exception {
        // arrange
        String inputJson = "{\"address\": \"5, road to Nantes\" , \"city\": \"Treillieres\" , \"zip\": \"12345\" , \"phone\": \"800-800-6789\" , \"email\": \"harry.corouge@gmail.com\"}";

        // act
        mockMvc.perform(put("/person/Unknown/Unknown")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isNotFound());
    }

    @Order(3)
    @Test
    void deletePerson_ShouldBeOK() throws Exception {

        mockMvc.perform(delete("/person/Harry/Corouge"))
                .andExpect(status().isOk());
    }

    @Test
    void deletePerson_ShouldReturnNotFound() throws Exception {

        mockMvc.perform(delete("/person/Unknown/Unknown"))
                .andExpect(status().isNotFound());
    }

}
