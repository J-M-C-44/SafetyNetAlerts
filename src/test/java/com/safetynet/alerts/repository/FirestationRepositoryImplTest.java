package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.service.FirestationServiceImpl;
import com.safetynet.alerts.service.JsonDataBaseService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.safetynet.alerts.repository.IFirestationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import com.safetynet.alerts.service.JsonDataBaseService;

@Component
@SpringBootTest
class FirestationRepositoryImplTest {
    /** Service to Mock */
//    @Mock
//    JsonDataBaseService jsonDataBaseServiceMock = mock(jsonDataBaseService.class);

//    @Autowired
//    JsonDataBaseService jsonDataBaseService;
//
//    private final JsonDataBaseService jsonDataBaseService;
////    private final JsonDataBaseService jsonDataBaseService = new JsonDataBaseService();
//
//    *//**//** Class to test *//**//*
//    FirestationRepositoryImpl firestationRepositoryImpl = new FirestationRepositoryImpl(jsonDataBaseService);
////    FirestationRepositoryImpl firestationRepositoryImpl = new FirestationRepositoryImpl(jsonDataBaseServiceMock);
//
//    private final Firestation firestation1 = new Firestation("5, road to Nantes",  "99");
//
//    @Test
//    void findByAddressAndStation_shouldReturnFirestation() {
//        //arrange
//        Firestation firestationToFind    = firestation1;
//        Optional<Firestation> firestationExpected = Optional.of(firestation1);
//
//        //act
//        Optional<Firestation> result = firestationRepositoryImpl.findByAddressAndStation(firestationToFind.getAddress(), firestationToFind.getStation());
//
//        //assert
//        assertThat(result).usingRecursiveComparison().isEqualTo(firestationExpected);
//
//    }
//    @Test
//    void findByAddressAndStation_shouldReturnEmpty() {
//        //arrange
//
//        //act
//        Optional<Firestation> result = firestationRepositoryImpl.findByAddressAndStation("wrongAddress", "666");
//
//        //assert
//        assertThat(result).isEmpty();
//    }
//
//    @Test
//    void findByAddress() {
//    }
//
//    @Test
//    void findByStation() {
//    }
//
//    @Test
//    void delete() {
//    }
//
//    @Test
//    void add() {
//    }
//
//    @Test
//    void update() {
//    }
//*//*}*/
}