package com.safetynet.alerts.service;

import com.safetynet.alerts.exception.AlreadyExistsException;
import com.safetynet.alerts.exception.NotFoundException;
import com.safetynet.alerts.model.Person;
import com.safetynet.alerts.repository.IPersonRepository;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {
    private final Person person1 = new Person("Harry","Covert","5, road to Nantes","Treillieres","12345","800-800-1234","harry.covert@gmail.com");
    private final Person person2 = new Person("Harry","Covert","5, road to Brest","Nantes","44000","800-800-2345","harry.covert2@gmail.com");
    private static final String FIRST_NAME = "Harry";
    private static final String LAST_NAME = "Covert";

    /** Service à mocker */
    @Mock
    IPersonRepository personRepositoryMock;
    /** Class à tester (avec injection des mocks)*/
    @InjectMocks
    PersonServiceImpl personServiceImpl;
    
    @Test
    void getPerson_ShouldBeOKAndReturnPerson() {
        //arrange
        Person personToFind = person1;
        when(personRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.of(personToFind));

        //act
        Optional<Person> result = personServiceImpl.getPerson(FIRST_NAME, LAST_NAME);
        Optional<Person> personExpected = Optional.of(person1);

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(personExpected);
        verify(personRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
    }

    @Test
    void addPerson_ShouldBeOKAndReturnPerson() {
        //arrange
        Person personToAdd = person1;
        when(personRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());
        when(personRepositoryMock.add(any(Person.class))).thenReturn(personToAdd);

        //act
        Person result = personServiceImpl.addPerson(personToAdd);
        Person personExpected = person1;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(personExpected);
        verify(personRepositoryMock,times(1)).add(personToAdd);
        verify(personRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
    }

    @Test
    void addPerson_ShouldReturnAlreadyExistsException() {
        //arrange
        Person personToAdd = person1;
        when(personRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.of(personToAdd));

        //act
        AlreadyExistsException exception = assertThrows(AlreadyExistsException.class,
                () -> personServiceImpl.addPerson(personToAdd));

        //assert
        assertThat(exception.getMessage()).isEqualTo("person with these firstname and lastname already exist !");
        verify(personRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
        verifyNoMoreInteractions(personRepositoryMock);
    }

    @Test
    void updatePerson_ShouldBeOKAndReturnPerson() {
        //arrange
        Person currentPerson  = person1;
        Person personToUpdate = person2;
        when(personRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.of(currentPerson));
        when(personRepositoryMock.update(any(Person.class), any(Person.class))).thenReturn(personToUpdate);

        //act
        Person result = personServiceImpl.updatePerson(personToUpdate);
        Person personExpected = person2;

        //assert
        assertThat(result).usingRecursiveComparison().isEqualTo(personExpected);
        verify(personRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
        verify(personRepositoryMock,times(1)).update(currentPerson,personToUpdate);
    }

    @Test
    void updatePerson_ShouldReturnNotFoundException() {
        //arrange
        Person personToUpdate = person1;
        when(personRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());

        //act
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> personServiceImpl.updatePerson(personToUpdate));

        //assert
        assertThat(exception.getMessage()).isEqualTo("person not found");
        verify(personRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
        verifyNoMoreInteractions(personRepositoryMock);
    }

    @Test
    void deletePerson_ShouldBeOK(){
        //arrange
        Person personToDelete = person1;
        when(personRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.of(personToDelete));

        //act
        personServiceImpl.deletePerson(FIRST_NAME, LAST_NAME);
        Person personExpected  = person1;

        //assert
        verify(personRepositoryMock,times(1)).delete(personExpected);
        verify(personRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
    }

    @Test
    void deletePerson_ShouldReturnNotFoundException(){
        //arrange
        when(personRepositoryMock.findByFirstNameAndLastName(anyString(),anyString())).thenReturn(Optional.empty());

        //act
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> personServiceImpl.deletePerson(FIRST_NAME, LAST_NAME));

        //assert
        assertThat(exception.getMessage()).isEqualTo("person not found");
        verify(personRepositoryMock,times(1)).findByFirstNameAndLastName("Harry", "Covert");
        verifyNoMoreInteractions(personRepositoryMock);
    }

    @Test
    void getCommunityEmails_ShouldBeOKAndReturnEmailsList() {
        //arrange
        List<String> emails = List.of("harry.covert@gmail.com", "harry.covert2@gmail.com");
        when(personRepositoryMock.findEmailsBycity(anyString())).thenReturn(emails);

        //act
        List<String> result = personServiceImpl.getCommunityEmails("Nantes");

        //assert
        assertThat(result).isEqualTo(emails);
        verify(personRepositoryMock,times(1)).findEmailsBycity("Nantes");
    }

}