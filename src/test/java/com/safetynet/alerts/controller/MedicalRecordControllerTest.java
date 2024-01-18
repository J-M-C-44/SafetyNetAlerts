package com.safetynet.alerts.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.controller.request.MapperDTO;
import com.safetynet.alerts.controller.request.medicalrecord.MedicalRecordDTO;
import com.safetynet.alerts.controller.request.medicalrecord.MedicalRecordWithoutNameDTO;

import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.service.IMedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
class MedicalRecordControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMedicalRecordService medicalRecordServiceMock;
    @MockBean
    private MapperDTO mapperDTOMock;

    ObjectMapper jsonMapper = new ObjectMapper();
    private final MedicalRecord medicalRecord1 = new MedicalRecord("Harry","Covert","12/31/2000", List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    private final MedicalRecordDTO medicalRecordDTO1 = new MedicalRecordDTO("Harry","Covert","12/31/2000", List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    
    @Test
    void getMedicalRecord_ShouldBeOKAndReturnMedicalRecordDTO() throws Exception {
        // arrange
        when(medicalRecordServiceMock.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.of(medicalRecord1));
        when(mapperDTOMock.medicalRecordToMedicalRecordDTO(any(MedicalRecord.class))).thenReturn(medicalRecordDTO1);

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

        verify(medicalRecordServiceMock,times(1)).getMedicalRecord("Harry","Covert");
        verifyNoMoreInteractions(medicalRecordServiceMock);
        verify(mapperDTOMock,times(1)).medicalRecordToMedicalRecordDTO(medicalRecord1);
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void getMedicalRecord_ShouldReturnNoContentWhenMedicalRecordNotFound() throws Exception {
        // arrange
        when(medicalRecordServiceMock.getMedicalRecord(anyString(), anyString())).thenReturn(Optional.empty());
        // act
        mockMvc.perform(get("/medicalRecord")
                        .param("firstName", "Harry")
                        .param("lastName", "Covert"))
        // assert
                .andExpect(status().isNoContent());

        verify(medicalRecordServiceMock,times(1)).getMedicalRecord("Harry","Covert");
        verifyNoMoreInteractions(medicalRecordServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test

    void createMedicalRecord_ShouldBeOKAndReturnMedicalRecordDTO()throws Exception {
        // arrange
        String inputJson = jsonMapper.writeValueAsString(medicalRecordDTO1);
        when(mapperDTOMock.medicalRecordDTOToMedicalRecord(any(MedicalRecordDTO.class))).thenReturn(medicalRecord1);
        when(medicalRecordServiceMock.addMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord1);
        when(mapperDTOMock.medicalRecordToMedicalRecordDTO(any(MedicalRecord.class))).thenReturn(medicalRecordDTO1);

        // act
        mockMvc.perform(post("/medicalRecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Harry"))
                .andExpect(jsonPath("$.lastName").value("Covert"))
                .andExpect(jsonPath("$.birthdate").value("12/31/2000"))
                .andExpect(jsonPath("$.medications[0]").value("suppo:1u"))
                .andExpect(jsonPath("$.medications[1]").value("paracetamol:500mg"))
                .andExpect(jsonPath("$.allergies[0]").value("bouleau"));

        verify(medicalRecordServiceMock,times(1)).addMedicalRecord(medicalRecord1);
        verifyNoMoreInteractions(medicalRecordServiceMock);
        verify(mapperDTOMock,times(1)).medicalRecordDTOToMedicalRecord(any(MedicalRecordDTO.class));
        verify(mapperDTOMock,times(1)).medicalRecordToMedicalRecordDTO(medicalRecord1);
        verifyNoMoreInteractions(mapperDTOMock);
    }
    
    @Test
    void updateMedicalRecord_ShouldBeOKAndReturnMedicalRecordDTO() throws Exception {
        // arrange
        String inputJson = "{\"birthdate\": \"12/31/2000\" , \"medications\": [\"suppo:1u\", \"paracetamol:500mg\"] , \"allergies\": [\"bouleau\"]}";
        when(mapperDTOMock.medicalRecordWithoutNameDTOToMedicalRecord(any(MedicalRecordWithoutNameDTO.class), anyString(),anyString())).thenReturn(medicalRecord1);
        when(medicalRecordServiceMock.updateMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord1);
        when(mapperDTOMock.medicalRecordToMedicalRecordDTO(any(MedicalRecord.class))).thenReturn(medicalRecordDTO1);

        // act
        mockMvc.perform(put("/medicalRecord/Harry/Covert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Harry"))
                .andExpect(jsonPath("$.lastName").value("Covert"))
                .andExpect(jsonPath("$.birthdate").value("12/31/2000"))
                .andExpect(jsonPath("$.medications[0]").value("suppo:1u"))
                .andExpect(jsonPath("$.medications[1]").value("paracetamol:500mg"))
                .andExpect(jsonPath("$.allergies[0]").value("bouleau"));

        verify(medicalRecordServiceMock,times(1)).updateMedicalRecord(medicalRecord1);
        verifyNoMoreInteractions(medicalRecordServiceMock);
        verify(mapperDTOMock,times(1)).medicalRecordWithoutNameDTOToMedicalRecord(any(MedicalRecordWithoutNameDTO.class), anyString(),anyString());
        verify(mapperDTOMock,times(1)).medicalRecordToMedicalRecordDTO(medicalRecord1);
        verifyNoMoreInteractions(mapperDTOMock);
    }
    
    @Test
    void deleteMedicalRecord_ShouldBeOK() throws Exception {

        mockMvc.perform(delete("/medicalRecord/Harry/Covert"))
                .andExpect(status().isOk());
        verify(medicalRecordServiceMock,times(1)).deleteMedicalRecord("Harry","Covert");
        verifyNoMoreInteractions(medicalRecordServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

}