package com.safetynet.alerts.controller;

import com.safetynet.alerts.controller.request.MapperDTO;
import com.safetynet.alerts.controller.request.tranverse.*;
import com.safetynet.alerts.model.MedicalRecord;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.IPersonService;
import com.safetynet.alerts.service.ITransverseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TransverseController.class)
class TransverseControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IPersonService personServiceMock;
    @MockBean
    private ITransverseService transverseServiceMock;
    @MockBean
    private MapperDTO mapperDTOMock;

    private final Person person1 = new Person("Harry","Covert","5, road to Nantes","Treillieres","12345","800-800-1234","harry.covert@gmail.com");
    private final Person person2 = new Person("Justine","Illusion","5, road to Nantes","Treillieres","12345","800-800-1234","justine.illusion@gmail.com");
    private final PersonsCoveredByStation personsCoveredByStation1 = new PersonsCoveredByStation(List.of(person1, person2),1,1);
    private final PersonsCoveredByStation personsCoveredByStation2 = new PersonsCoveredByStation(Collections.emptyList(),0,0);
    private final PersonCoveredByStationDTO personCoveredByStationDTO1 = new PersonCoveredByStationDTO("Harry","Covert","5, road to Nantes","800-800-1234");
    private final PersonCoveredByStationDTO personCoveredByStationDTO2 = new PersonCoveredByStationDTO("Justine","Illusion","5, road to Nantes","800-800-1234");
    private final PersonsCoveredByStationDTO personsCoveredByStationDTO1 = new PersonsCoveredByStationDTO(List.of(personCoveredByStationDTO1, personCoveredByStationDTO2),1,1);
    private final MedicalRecord medicalRecord1 = new MedicalRecord("Harry","Covert","12/31/2000", List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    ChildAndHomeMembers childAndHomeMembers1 = new ChildAndHomeMembers(medicalRecord1, 7, List.of(person1));
    private final ChildAlertDTO childAlertDTO1 = new ChildAlertDTO("Justine","Illusion", 7, List.of(person1));
    private final PhonesAlertDTO phonesAlertDTO = new PhonesAlertDTO(List.of("800-800-1234", "800-800-2345"));
    private final PersonAndMedicalRecordWithAge personAndMedicalRecordWithAge1 = new PersonAndMedicalRecordWithAge(person1,medicalRecord1,23);
    private final PersonAndMedicalRecordWithAge personAndMedicalRecordWithAge2 = new PersonAndMedicalRecordWithAge(person2,medicalRecord1,22);
    private final StationAndCoveredPersonsAndMedicalRecordWithAge stationAndCoveredPersonsAndMedicalRecordWithAge = new StationAndCoveredPersonsAndMedicalRecordWithAge("99",List.of(personAndMedicalRecordWithAge1, personAndMedicalRecordWithAge2));
    PersonForFireDTO personForFireDTO1 = new PersonForFireDTO("Harry","Covert", "800-800-1234", 23, List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    PersonForFireDTO personForFireDTO2 = new PersonForFireDTO("Justine","Illusion", "800-800-1234", 22, List.of("suppo:2u", "paracetamol:1000mg"), List.of(""));
    private final FireDTO fireDTO = new FireDTO("99", List.of(personForFireDTO1, personForFireDTO2));
    PersonFloodDTO personFloodDTO1 = new PersonFloodDTO("Harry","Covert", "800-800-1234", 23, List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    PersonFloodDTO personFloodDTO2 = new PersonFloodDTO("Justine","Illusion", "800-800-1234", 22, List.of("suppo:2u", "paracetamol:1000mg"), List.of(""));
    PersonInfoDTO personInfoDTO1 = new PersonInfoDTO("Harry","Covert", "harry.covert@gmail.com",23, List.of("suppo:1u", "paracetamol:500mg"), List.of("bouleau"));
    List<String> emails = List.of("harry.covert@gmail.com","justine.illusion@gmail.com");
    CommunityEmailsDTO communityEmailsDTO = new CommunityEmailsDTO(emails);

    @Test
    void getPersonsCoveredByStation_ShouldReturnPersonsCoveredByStationDTO() throws Exception {
        // arrange
        when(transverseServiceMock.getPersonsCoveredByStation(anyString())).thenReturn(personsCoveredByStation1);
        when(mapperDTOMock.personsCoveredByStationToPersonsCoveredByStationDTO(any(PersonsCoveredByStation.class))).thenReturn(personsCoveredByStationDTO1);

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
                .andExpect(jsonPath("$.persons[1].phone").value("800-800-1234"))
                .andExpect(jsonPath("$.adultsCount").value(1))
                .andExpect(jsonPath("$.childrenCount").value(1));
        verify(transverseServiceMock,times(1)).getPersonsCoveredByStation("99");
        verifyNoMoreInteractions(personServiceMock);
        verify(mapperDTOMock,times(1)).personsCoveredByStationToPersonsCoveredByStationDTO(personsCoveredByStation1);
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void getPersonsCoveredByStation_ShouldReturnNoContentWhenFirestationOrPersonNotFound() throws Exception {
        // arrange
        when(transverseServiceMock.getPersonsCoveredByStation(anyString())).thenReturn(personsCoveredByStation2);

        // act
        mockMvc.perform(get("/firestation")
                        .param("stationNumber", "unknown"))
        // assert
                .andExpect(status().isNoContent());
        verify(transverseServiceMock,times(1)).getPersonsCoveredByStation("unknown");
        verifyNoMoreInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void getChildrenAndHomeMembers_ShouldBeOKAndReturnChildAlertDTO() throws Exception {
        // arrange
        when(transverseServiceMock.getChildrenAndHomeMembersByAddress(anyString())).thenReturn(List.of(childAndHomeMembers1));
        when(mapperDTOMock.childrenAndHomeMembersToChildAlertDTO(List.of(childAndHomeMembers1))).thenReturn(List.of(childAlertDTO1));

        // act
        mockMvc.perform(get("/childAlert")
                        .param("address", "5, road to Nantes"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Justine"))
                .andExpect(jsonPath("$[0].lastName").value("Illusion"))
                .andExpect(jsonPath("$[0].age").value(7))
                .andExpect(jsonPath("$[0].homeMembers[0].firstName").value("Harry"))
                .andExpect(jsonPath("$[0].homeMembers[0].lastName").value("Covert"))
                .andExpect(jsonPath("$[0].homeMembers[0].address").value("5, road to Nantes"))
                .andExpect(jsonPath("$[0].homeMembers[0].phone").value("800-800-1234"));
        verify(transverseServiceMock,times(1)).getChildrenAndHomeMembersByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personServiceMock);
        verify(mapperDTOMock,times(1)).childrenAndHomeMembersToChildAlertDTO(List.of(childAndHomeMembers1));
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void getChildrenAndHomeMembers_ShouldReturnNoContent() throws Exception {
        // arrange
        when(transverseServiceMock.getChildrenAndHomeMembersByAddress(anyString())).thenReturn(Collections.emptyList());

        // act
        mockMvc.perform(get("/childAlert")
                        .param("address", "unknown"))
        // assert
                .andExpect(status().isNoContent());
        verify(transverseServiceMock,times(1)).getChildrenAndHomeMembersByAddress("unknown");
        verifyNoMoreInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void getPhonesByStation_ShouldBeOKAndReturnPhonesAlertDTO() throws Exception {
        // arrange
        when(transverseServiceMock.getPersonsByStation(anyString())).thenReturn(List.of(person1,person2));
        when(mapperDTOMock.personsToPhonesAlertDTO(List.of(person1,person2))).thenReturn(phonesAlertDTO);

        // act
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "99"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phones[0]").value("800-800-1234"))
                .andExpect(jsonPath("$.phones[1]").value("800-800-2345"));
        verify(transverseServiceMock,times(1)).getPersonsByStation("99");
        verifyNoMoreInteractions(personServiceMock);
        verify(mapperDTOMock,times(1)).personsToPhonesAlertDTO(List.of(person1,person2));
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void getPhonesByStation_ShouldReturnNoContent() throws Exception {
        // arrange
        when(transverseServiceMock.getPersonsByStation(anyString())).thenReturn(Collections.emptyList());

        // act
        mockMvc.perform(get("/phoneAlert")
                        .param("firestation", "unknown"))
        // assert
                .andExpect(status().isNoContent());
        verify(transverseServiceMock,times(1)).getPersonsByStation("unknown");
        verifyNoMoreInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);

    }

    @Test
    void getPersonsForFirebByAddress_ShouldBeOKAndReturnFireDTO() throws Exception {
        // arrange
        when(transverseServiceMock.getPersonsForFireByAddress(anyString())).thenReturn(stationAndCoveredPersonsAndMedicalRecordWithAge);
        when(mapperDTOMock.stationAndCoveredPersonAndMedicalRecordWithAgeToFireDTO(any(StationAndCoveredPersonsAndMedicalRecordWithAge.class))).thenReturn(fireDTO);

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
                .andExpect(jsonPath("$.persons[1].phone").value("800-800-1234"))
                .andExpect(jsonPath("$.persons[1].age").value("22"))
                .andExpect(jsonPath("$.persons[1].medications[0]").value("suppo:2u"))
                .andExpect(jsonPath("$.persons[1].medications[1]").value("paracetamol:1000mg"))
                .andExpect(jsonPath("$.persons[1].allergies[0]").value(""));
        verify(transverseServiceMock,times(1)).getPersonsForFireByAddress("5, road to Nantes");
        verifyNoMoreInteractions(personServiceMock);
        verify(mapperDTOMock,times(1)).stationAndCoveredPersonAndMedicalRecordWithAgeToFireDTO(stationAndCoveredPersonsAndMedicalRecordWithAge);
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void getPersonsForFirebByAddress_ShouldReturnNoContent() throws Exception {
        // arrange
        when(transverseServiceMock.getPersonsForFireByAddress(anyString())).thenReturn(new StationAndCoveredPersonsAndMedicalRecordWithAge("99", Collections.emptyList()));

        // act
        mockMvc.perform(get("/fire")
                        .param("address", "unknown"))
        // assert
                .andExpect(status().isNoContent());
        verify(transverseServiceMock,times(1)).getPersonsForFireByAddress("unknown");
        verifyNoMoreInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void getPersonsForFloodByStations_ShouldBeOKAndReturnAddressWithCorrespondingPersonsAndMedicalData() throws Exception {
        // arrange
        when(transverseServiceMock.getPersonsForFloodByStations(anyList())).thenReturn(Map.of("5, road to Nantes", List.of(personAndMedicalRecordWithAge1, personAndMedicalRecordWithAge2)));
        when(mapperDTOMock.personAndMedicalRecordWithAgeToFloodDTO(anyMap())).thenReturn(Map.of("5, road to Nantes", List.of(personFloodDTO1, personFloodDTO2)));

        // act
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "99,88"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].firstName").value("Harry"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].lastName").value("Covert"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].phone").value("800-800-1234"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].age").value("23"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].medications[0]").value("suppo:1u"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].medications[1]").value("paracetamol:500mg"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[0].allergies[0]").value("bouleau"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[1].firstName").value("Justine"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[1].lastName").value("Illusion"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[1].phone").value("800-800-1234"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[1].age").value("22"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[1].medications[0]").value("suppo:2u"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[1].medications[1]").value("paracetamol:1000mg"))
                .andExpect(jsonPath("$.['5, road to Nantes'].[1].allergies[0]").value(""));
        verify(transverseServiceMock,times(1)).getPersonsForFloodByStations(List.of("99","88"));
        verifyNoMoreInteractions(personServiceMock);
        verify(mapperDTOMock,times(1)).personAndMedicalRecordWithAgeToFloodDTO(anyMap());
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void getPersonsForFloodByStations_ShouldReturnNoContent() throws Exception {
        // arrange
        when(transverseServiceMock.getPersonsForFloodByStations(anyList())).thenReturn(Collections.emptyMap());

        // act
        mockMvc.perform(get("/flood/stations")
                        .param("stations", "99,88"))
        // assert
                .andExpect(status().isNoContent());
        verify(transverseServiceMock,times(1)).getPersonsForFloodByStations(List.of("99","88"));
        verifyNoMoreInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void getPersonInfoByName_ShouldBeOKAndReturnPersonsInformations() throws Exception {
        // arrange
        when(transverseServiceMock.getPersonInfoByName(anyString(),anyString())).thenReturn(List.of(personAndMedicalRecordWithAge1, personAndMedicalRecordWithAge1));
        when(mapperDTOMock.personAndMedicalRecordWithAgeToPersonsInfoDTO(List.of(personAndMedicalRecordWithAge1, personAndMedicalRecordWithAge1))).thenReturn(List.of(personInfoDTO1, personInfoDTO1));

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
                .andExpect(jsonPath("$.[0].allergies[0]").value("bouleau"))
                .andExpect(jsonPath("$.[1].firstName").value("Harry"))
                .andExpect(jsonPath("$.[1].lastName").value("Covert"))
                .andExpect(jsonPath("$.[1].email").value("harry.covert@gmail.com"))
                .andExpect(jsonPath("$.[1].age").value("23"))
                .andExpect(jsonPath("$.[1].medications[0]").value("suppo:1u"))
                .andExpect(jsonPath("$.[1].medications[1]").value("paracetamol:500mg"))
                .andExpect(jsonPath("$.[1].allergies[0]").value("bouleau"));
        verify(transverseServiceMock,times(1)).getPersonInfoByName("Harry", "Covert");
        verifyNoMoreInteractions(personServiceMock);
        verify(mapperDTOMock,times(1)).personAndMedicalRecordWithAgeToPersonsInfoDTO(List.of(personAndMedicalRecordWithAge1, personAndMedicalRecordWithAge1));
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void getPersonInfoByName_ShouldReturnNoContent() throws Exception {
        // arrange
        when(transverseServiceMock.getPersonInfoByName(anyString(),anyString())).thenReturn(Collections.emptyList());

        // act
        mockMvc.perform(get("/personInfo")
                        .param("firstName", "unknown")
                        .param("lastName", "unknown"))
        // assert
                .andExpect(status().isNoContent());
        verify(transverseServiceMock,times(1)).getPersonInfoByName("unknown", "unknown");
        verifyNoMoreInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

    @Test
    void getCommunityEmail_ShouldBeOKAndReturnEmails() throws Exception {
        // arrange
        when(personServiceMock.getCommunityEmails(anyString())).thenReturn(emails);
        when(mapperDTOMock.emailsToCommunityEmailsDTO(emails)).thenReturn(communityEmailsDTO);


        // act
        mockMvc.perform(get("/communityEmail")
                        .param("city", "Treillieres"))
        // assert
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.emails[0]").value("harry.covert@gmail.com"))
                .andExpect(jsonPath("$.emails[1]").value("justine.illusion@gmail.com"));
        verify(personServiceMock,times(1)).getCommunityEmails("Treillieres");
        verifyNoMoreInteractions(personServiceMock);
        verify(mapperDTOMock,times(1)).emailsToCommunityEmailsDTO(emails);
        verifyNoMoreInteractions(mapperDTOMock);
    }

    @Test
    void getCommunityEmail_ShouldReturnNoContent() throws Exception {
        // arrange
        when(personServiceMock.getCommunityEmails(anyString())).thenReturn(Collections.emptyList());

        // act
        mockMvc.perform(get("/communityEmail")
                        .param("city", "unknown"))
                // assert
                .andExpect(status().isNoContent());
        verify(personServiceMock,times(1)).getCommunityEmails("unknown");
        verifyNoMoreInteractions(personServiceMock);
        verifyNoInteractions(mapperDTOMock);
    }

}