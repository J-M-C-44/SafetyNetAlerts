package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.JsonDataBase;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.model.Person;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JsonDataBaseService implements CommandLineRunner {

    private JsonDataBase dataBase;
    ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    public void run(String[] args) throws IOException {
        // charger données depuis fichier JSON au démarrage

        // TODO chemin fichier à mettre en properties
        // TODO voir pourquoi les attributs de firestations et medicalrecords ne fonctionnent pas avec jakson ???
            // --> a priori ok pour cload firestation mais reste pb de local date / medical records
            // --> mais KO sur update Person maintenant ....
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.dataBase = jsonMapper.readValue(new File("src/main/resources/data2.json"), JsonDataBase.class);
        System.out.println("chargement des données JSON au démarrage :" + this.dataBase.toString());
    }

    public List<Person> getPersons() {
        return this.dataBase.getPersons();
    }

    public List<Firestation> getFirestations() {
        return this.dataBase.getFirestations();
    }
    public List<Medicalrecord> getMedicalrecords() {
        return this.dataBase.getMedicalrecords();
    }

    public void saveDataBaseInFile() throws IOException {
        jsonMapper.writeValue(new File("src/main/resources/data2.json"), this.dataBase);
    }
}
