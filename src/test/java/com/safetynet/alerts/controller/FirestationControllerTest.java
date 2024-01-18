package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.request.MapperDTO;
import com.safetynet.alerts.controller.request.firestation.FirestationDTO;
import com.safetynet.alerts.controller.request.firestation.FirestationWithoutAddressDTO;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.IFirestationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FirestationController.class)
class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFirestationService firestationServiceMock;
    @MockBean
    private MapperDTO mapperDTOMock;

    ObjectMapper jsonMapper = new ObjectMapper();
    private final FirestationDTO firestationDTO1 = new FirestationDTO("5, road to Nantes",  "99");
    private final Firestation firestation1 = new Firestation("5, road to Nantes",  "99");

    @Test
    void createFirestation_ShouldBeOKAndReturnFirestationDTO() throws Exception {
        // arrange
        String inputJson = jsonMapper.writeValueAsString(firestationDTO1);
        when(mapperDTOMock.firestationDTOToFirestation(any(FirestationDTO.class))).thenReturn(firestation1);
        when(firestationServiceMock.addFirestation(any(Firestation.class))).thenReturn(firestation1);
        when(mapperDTOMock.firestationToFirestationDTO(any(Firestation.class))).thenReturn(firestationDTO1);

        // act
        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.address").value("5, road to Nantes"))
                .andExpect(jsonPath("$.station").value("99"));

        verify(firestationServiceMock,times(1)).addFirestation(firestation1);
        verifyNoMoreInteractions(firestationServiceMock);
        verify(mapperDTOMock,times(1)).firestationDTOToFirestation(any(FirestationDTO.class));
        verify(mapperDTOMock,times(1)).firestationToFirestationDTO(firestation1);
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void updateFirestation_ShouldBeOKAndReturnFirestationDTO() throws Exception{
        String inputJson = "{\"station\": \"99\"}";
        when(mapperDTOMock.firestationWithoutAdressDTOToFirestation(any(FirestationWithoutAddressDTO.class), anyString())).thenReturn(firestation1);
        when(firestationServiceMock.updateFirestation(any(Firestation.class))).thenReturn(firestation1);
        when(mapperDTOMock.firestationToFirestationDTO(any(Firestation.class))).thenReturn(firestationDTO1);

        // act
        mockMvc.perform(put("/firestation/5, road to Nantes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.address").value("5, road to Nantes"))
                .andExpect(jsonPath("$.station").value("99"));

        verify(firestationServiceMock,times(1)).updateFirestation(firestation1);
        verifyNoMoreInteractions(firestationServiceMock);
        verify(mapperDTOMock,times(1)).firestationWithoutAdressDTOToFirestation(any(FirestationWithoutAddressDTO.class),anyString());
        verify(mapperDTOMock,times(1)).firestationToFirestationDTO(firestation1);
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void deleteFirestationByAddress_ShouldBeOK() throws Exception {

        mockMvc.perform(delete("/firestation/5, road to Nantes"))
                .andExpect(status().isOk());
        verify(firestationServiceMock,times(1)).deleteFirestationByAddress("5, road to Nantes");
        verifyNoMoreInteractions(firestationServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void deleteFirestationByStation_ShouldBeOK() throws Exception {

        mockMvc.perform(delete("/firestation")
                        .param("station", "99"))
                .andExpect(status().isOk());
        verify(firestationServiceMock,times(1)).deleteFirestationByStation("99");
        verifyNoMoreInteractions(firestationServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }
}