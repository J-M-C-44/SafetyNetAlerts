package com.safetynet.alerts.repository;

import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.service.JsonDataBaseService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonRepositoryImplTest {

    @Mock
    JsonDataBaseService jsonDataBaseServiceMock;

    @InjectMocks
    PersonRepositoryImpl personRepositoryImpl;

    private final Person person1 = new Person("Harry","Covert","5, road to Nantes","Treillieres","12345","800-800-1234","harry.covert@gmail.com");
    private final Person person2 = new Person("Justine","Illusion","5, road to Nantes","Treillieres","12345","800-800-1234","justine.illusion@gmail.com");
    private final Person person3 = new Person("Paul","Emploi","6, street of Brest","Nantes","23456","800-800-2345","paul.emploi@gmail.com");
    private final Person person4 = new Person("Harry","Covert","8, street of Quimper","Treillieres","12345","800-800-456","harry.covert2@gmail.com");
    private final List<Person> persons = List.of(person1, person2,person3, person4);
    private static final String FIRST_NAME = "Harry";
    private static final String LAST_NAME = "Covert";
    private static final String ADDRESS = "5, road to Nantes";
    private static final String CITY = "Treillieres";

    @Test
    void findByFirstNameAndLastName_ShouldBeOKAndReturnPerson() {
        //arrange
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(persons);

        //act
        Optional<Person> result = personRepositoryImpl.findByFirstNameAndLastName(FIRST_NAME, LAST_NAME);
        Optional<Person> expected = Optional.of(person1);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findByFirstNameAndLastName_ShouldReturnEmptyWhenPersonNotFound() {
        //arrange
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(persons);

        //act
        Optional<Person> result = personRepositoryImpl.findByFirstNameAndLastName("unknown", "unknown");
        Optional<Person> expected = Optional.empty();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findAllByFirstNameAndLastName_ShouldBeOKAndReturnPerson() {
        //arrange
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(persons);

        //act
        List<Person> result = personRepositoryImpl.findAllByFirstNameAndLastName(FIRST_NAME, LAST_NAME);
        List<Person> expected = List.of(person1, person4);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findALLByFirstNameAndLastName_ShouldReturnEmptyWhenPersonNotFound() {
        //arrange
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(persons);

        //act
        List<Person> result = personRepositoryImpl.findAllByFirstNameAndLastName("unknown","unknown");
        List<Person> expected = Collections.emptyList();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findByAddress_ShouldBeOKAndReturnPerson() {
        //arrange
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(persons);

        //act
        List<Person> result = personRepositoryImpl.findByAddress(ADDRESS);
        List<Person> expected = List.of(person1, person2);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findByAddress_ShouldReturnEmptyWhenPersonNotFound() {
        //arrange
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(persons);

        //act
        List<Person> result = personRepositoryImpl.findByAddress("unknown");
        List<Person> expected = Collections.emptyList();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void delete_ShouldBeOK() {
        //arrange
        List<Person> personsMock = mock(List.class);
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(personsMock);

        //act
        personRepositoryImpl.delete(person1);

        //assert
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verify(personsMock,times(1)).remove(person1);
        verify(jsonDataBaseServiceMock,times(1)).saveDataBaseInFile();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
        verifyNoMoreInteractions(personsMock);
    }

    @Test
    void add_ShouldBeOK() {
        //arrange
        List<Person> personsMock = mock(List.class);
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(personsMock);

        //act
        Person result = personRepositoryImpl.add(person1);
        Person expected = person1;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verify(personsMock,times(1)).add(person1);
        verify(jsonDataBaseServiceMock,times(1)).saveDataBaseInFile();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
        verifyNoMoreInteractions(personsMock);
    }

    @Test
    void update_ShouldBeOK() {
        //arrange
        List<Person> personsMock = mock(List.class);
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(personsMock);

        //act
        Person result = personRepositoryImpl.update(person1, person2);
        Person expected = person2;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verify(personsMock,times(1)).indexOf(person1);
        verify(personsMock,times(1)).set(0,person2);
        verify(jsonDataBaseServiceMock,times(1)).saveDataBaseInFile();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
        verifyNoMoreInteractions(personsMock);
    }

    @Test
    void findEmailsBycity_ShouldBeOKAndReturnEmails() {
        //arrange
        List<String> emails = List.of(person1.getEmail(), person2.getEmail(),person4.getEmail());
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(persons);

        //act
        List<String> result = personRepositoryImpl.findEmailsBycity(CITY);
        List<String> expected = emails;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }

    @Test
    void findEmailsBycity_ShouldReturnEmptyWhenPersonNotFound() {
        //arrange
        when(jsonDataBaseServiceMock.getPersons()).thenReturn(persons);

        //act
        List<String> result = personRepositoryImpl.findEmailsBycity("unknown");
        List<String> expected = Collections.emptyList();

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        verify(jsonDataBaseServiceMock,times(1)).getPersons();
        verifyNoMoreInteractions(jsonDataBaseServiceMock);
    }
}

