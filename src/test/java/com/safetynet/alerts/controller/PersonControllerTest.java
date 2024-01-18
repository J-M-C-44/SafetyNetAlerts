package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.request.MapperDTO;
import com.safetynet.alerts.controller.request.person.PersonDTO;
import com.safetynet.alerts.controller.request.person.PersonWithoutNameDTO;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPersonService personServiceMock;
    @MockBean
    private MapperDTO mapperDTOMock;

    ObjectMapper jsonMapper = new ObjectMapper();
    private final Person person1 = new Person("Harry","Covert","5, road to Nantes","Treillieres","12345","800-800-1234","harry.covert@gmail.com");
    private final PersonDTO personDTO1 = new PersonDTO("Harry","Covert","5, road to Nantes","Treillieres","12345","800-800-1234","harry.covert@gmail.com");

    @Test
    void getPerson_ShouldBeOKAndReturnPersonDTO() throws Exception {
        // arrange
        when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(Optional.of(person1));
        when(mapperDTOMock.personToPersonDTO(any(Person.class))).thenReturn(personDTO1);

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

        verify(personServiceMock,times(1)).getPerson("Harry","Covert");
        verifyNoMoreInteractions(personServiceMock);
        verify(mapperDTOMock,times(1)).personToPersonDTO(person1);
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void getPerson_ShouldReturnNoContentWhenPersonNotFound() throws Exception {
        // arrange
        when(personServiceMock.getPerson(anyString(), anyString())).thenReturn(Optional.empty());
        // act
        mockMvc.perform(get("/person")
                        .param("firstName", "Harry")
                        .param("lastName", "Covert"))
        // assert
                .andExpect(status().isNoContent());
        verify(personServiceMock,times(1)).getPerson("Harry","Covert");
        verifyNoMoreInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void getPerson_ShouldReturnBadRequestWhenMissingParam() throws Exception {
        // act
        mockMvc.perform(get("/person")
                        .param("firstName", "Harry"))
        // assert
                .andExpect(status().isBadRequest());
        verifyNoInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void createPerson_ShouldBeOKAndReturnPersonDTO()throws Exception {
        // arrange
        String inputJson = jsonMapper.writeValueAsString(personDTO1);
        when(mapperDTOMock.personDTOToPerson(any(PersonDTO.class))).thenReturn(person1);
        when(personServiceMock.addPerson(any(Person.class))).thenReturn(person1);
        when(mapperDTOMock.personToPersonDTO(any(Person.class))).thenReturn(personDTO1);

        // act
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Harry"))
                .andExpect(jsonPath("$.lastName").value("Covert"))
                .andExpect(jsonPath("$.address").value("5, road to Nantes"))
                .andExpect(jsonPath("$.city").value("Treillieres"))
                .andExpect(jsonPath("$.zip").value("12345"))
                .andExpect(jsonPath("$.phone").value("800-800-1234"))
                .andExpect(jsonPath("$.email").value("harry.covert@gmail.com"));

        verify(personServiceMock,times(1)).addPerson(person1);
        verifyNoMoreInteractions(personServiceMock);
        verify(mapperDTOMock,times(1)).personDTOToPerson(any(PersonDTO.class));
        verify(mapperDTOMock,times(1)).personToPersonDTO(person1);
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void createPerson_ShouldReturnBadRequestWhenMissingFields() throws Exception {
        // arrange
        String inputJson = "{\"firstname\": \"Harry\" ,\"lastname\": \"Basmati\" , \"zip\": \"12345\" , \"phone\": \"800-800-8888\"}";

        // act
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isBadRequest());
        verifyNoInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void createPerson_ShouldReturnBadRequestWhenIncorrectFields() throws Exception {
        // arrange
        String inputJson = "{\"firstname\": \"Harry\" ,\"lastname\": \"Basmati\" , \"address\": \"5, road to Nantes\" , \"city\": \"Treillieres\" , \"zip\": \"12345\" , \"phone\": \"800-800-6789\" , \"email\": \"harry.basmatigmail.com\"}";

        // act
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isBadRequest());
        verifyNoInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void createPerson_ShouldReturnBadRequestWhenIncorrectJSON() throws Exception {
        // arrange
        String incorrectInputJson = "\"firstname\": \"Harry\" ,\"lastname\": \"Basmati\" , \"address\": \"5, road to Nantes\" , \"city\": \"Treillieres\" , \"zip\": \"12345\" , \"phone\": \"800-800-6789\" , \"email\": \"harry.basmatigmail.com\"}";

        // act
        mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(incorrectInputJson))
        // assert
                .andExpect(status().isBadRequest());
        verifyNoInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void updatePerson_ShouldBeOKAndReturnPersonDTO() throws Exception {
        // arrange
        String inputJson = "{\"address\": \"5, road to Nantes\" , \"city\": \"Treillieres\" , \"zip\": \"12345\" , \"phone\": \"800-800-1234\" , \"email\": \"harry.covert@gmail.com\"}";
        when(mapperDTOMock.personWithoutNameDTOToPerson(any(PersonWithoutNameDTO.class), anyString(),anyString())).thenReturn(person1);
        when(personServiceMock.updatePerson(any(Person.class))).thenReturn(person1);
        when(mapperDTOMock.personToPersonDTO(any(Person.class))).thenReturn(personDTO1);

        // act
        mockMvc.perform(put("/person/Harry/Covert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Harry"))
                .andExpect(jsonPath("$.lastName").value("Covert"))
                .andExpect(jsonPath("$.city").value("Treillieres"))
                .andExpect(jsonPath("$.address").value("5, road to Nantes"))
                .andExpect(jsonPath("$.zip").value("12345"))
                .andExpect(jsonPath("$.phone").value("800-800-1234"))
                .andExpect(jsonPath("$.email").value("harry.covert@gmail.com"));

        verify(personServiceMock,times(1)).updatePerson(person1);
        verifyNoMoreInteractions(personServiceMock);
        verify(mapperDTOMock,times(1)).personWithoutNameDTOToPerson(any(PersonWithoutNameDTO.class), anyString(),anyString());
        verify(mapperDTOMock,times(1)).personToPersonDTO(person1);
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void deletePerson_ShouldBeOK() throws Exception {

        mockMvc.perform(delete("/person/Harry/Covert"))
                .andExpect(status().isOk());
        verify(personServiceMock,times(1)).deletePerson("Harry","Covert");
        verifyNoMoreInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void deletePerson_ShouldBeReturnBadRequestWhenIncorrectPathVariable() throws Exception {

        mockMvc.perform(delete("/person/ /Covert"))
                .andExpect(status().isBadRequest());
        verifyNoInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

}