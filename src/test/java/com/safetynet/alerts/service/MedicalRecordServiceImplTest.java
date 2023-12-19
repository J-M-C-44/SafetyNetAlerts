package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.repository.IMedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalRecordServiceImplTest {
    private final MedicalRecord medicalRecord1 = new MedicalRecord("Harry","Covert","12/31/2000", List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    private final MedicalRecord medicalRecord2 = new MedicalRecord("Harry","Covert","11/30/2001", List.of("suppo:2u", "paracetamol:1000mg"), List.of(""));
    private static final String firstName = "Harry";
    private static final String lastName = "Covert";

    /** Service à mocker */
    @Mock
    IMedicalRecordRepository medicalRecordRepositoryMock;
    /** Class à tester (avec injection des mocks)*/
    @InjectMocks
    MedicalRecordServiceImpl medicalRecordServiceImpl;

    @Test
    void getMedicalRecord_shouldBeOKandReturnMedicalRecord() {
        //arrange
        MedicalRecord medicalRecordToFind = medicalRecord1;
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.of(medicalRecordToFind));

        //act
        Optional<MedicalRecord> result = medicalRecordServiceImpl.getMedicalRecord(firstName,lastName);
        Optional<MedicalRecord> medicalRecordExpected = Optional.of(medicalRecord1);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(medicalRecordExpected);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
    }
    
    @Test
    void addMedicalRecord_shouldBeOKandReturnMedicalRecord() {
        //arrange
        MedicalRecord medicalRecordToAdd = medicalRecord1;
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());
        when(medicalRecordRepositoryMock.add(any(MedicalRecord.class))).thenReturn(medicalRecordToAdd);

        //act
        MedicalRecord result = medicalRecordServiceImpl.addMedicalRecord(medicalRecordToAdd);
        MedicalRecord medicalRecordExpected = medicalRecord1;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(medicalRecordExpected);
        verify(medicalRecordRepositoryMock,times(1)).add(medicalRecordToAdd);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
    }

    @Test
    void addMedicalRecord_shouldReturnAlreadyExistsException() {
        //arrange
        MedicalRecord medicalRecordToAdd = medicalRecord1;
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.of(medicalRecordToAdd));

        //act
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> medicalRecordServiceImpl.addMedicalRecord(medicalRecordToAdd));

        //assert
        assertThat(exception.getMessage()).isEqualTo("medicalRecord with these firstname and lastname already exist !");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void updateMedicalRecord_shouldBeOKandReturnMedicalRecord() {
        //arrange
        MedicalRecord currentMedicalRecord  = medicalRecord1;
        MedicalRecord medicalRecordToUpdate = medicalRecord2;
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.of(currentMedicalRecord));
        when(medicalRecordRepositoryMock.update(any(MedicalRecord.class), any(MedicalRecord.class))).thenReturn(medicalRecordToUpdate);

        //act
        MedicalRecord result = medicalRecordServiceImpl.updateMedicalRecord(medicalRecordToUpdate);
        MedicalRecord medicalRecordExpected = medicalRecord2;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(medicalRecordExpected);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
        verify(medicalRecordRepositoryMock,times(1)).update(currentMedicalRecord,medicalRecordToUpdate);
    }
    
    @Test
    void updateMedicalRecord_shouldReturnNotFoundException() {
        //arrange
        MedicalRecord medicalRecordToUpdate = medicalRecord1;
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());

        //act
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> medicalRecordServiceImpl.updateMedicalRecord(medicalRecordToUpdate));

        //assert
        assertThat(exception.getMessage()).isEqualTo("medicalRecord not found");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

    @Test
    void deleteMedicalRecord_shouldBeOK(){
        //arrange
        MedicalRecord medicalRecordToDelete = medicalRecord1;
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.of(medicalRecordToDelete));

        //act
        medicalRecordServiceImpl.deleteMedicalRecord(firstName,lastName);
        MedicalRecord medicalRecordExpected  = medicalRecord1;

        //assert
        verify(medicalRecordRepositoryMock,times(1)).delete(medicalRecordExpected);
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
    }

    @Test
    void deleteMedicalRecord_shouldReturnNotFoundException(){
        //arrange
        when(medicalRecordRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());

        //act
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> medicalRecordServiceImpl.deleteMedicalRecord(firstName,lastName));

        //assert
        assertThat(exception.getMessage()).isEqualTo("medicalRecord not found");
        verify(medicalRecordRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
        verifyNoMoreInteractions(medicalRecordRepositoryMock);
    }

}