package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.alerts.config.CustomProperties;
import com.safetynet.alerts.controller.PersonController;
import com.safetynet.alerts.model.Firestation;
import com.safetynet.alerts.model.JsonDataBase;
import com.safetynet.alerts.model.Medicalrecord;
import com.safetynet.alerts.model.Person;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class JsonDataBaseService implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(JsonDataBaseService.class);
    private final CustomProperties customProperties;

    private JsonDataBase dataBase;
    ObjectMapper jsonMapper = new ObjectMapper();

    public JsonDataBaseService(CustomProperties customProperties) {
        this.customProperties = customProperties;
    }

    @Override
    public void run(String[] args) throws IOException {
        // charger données depuis fichier JSON au démarrage
        // TODO voir pourquoi les attributs de firestations et medicalrecords ne fonctionnent pas avec jakson ???
            // --> a priori ok pour load firestation mais reste pb de local date / medical records
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        logger.debug("db - going to Load Database from JSON file : " + customProperties.getJsonfilepathname());
        // TODO : voir si try and catch ou global exeption handler (ou mix des 2 try catch avec exception specifique ?
        this.dataBase = jsonMapper.readValue(new File(customProperties.getJsonfilepathname()), JsonDataBase.class);
        logger.info("Loading jsonDatabase complete");
    }

    public List<Person> getPersons() {
        logger.debug("      db - going to access persons from Jsondatabase");
        return this.dataBase.getPersons();
    }

    public List<Firestation> getFirestations() {
        logger.debug("      db - going to access firestations from Jsondatabase");
        return this.dataBase.getFirestations();
    }
    public List<Medicalrecord> getMedicalrecords() {
        logger.debug("      db - going to access medicalrecords from Jsondatabase");
        return this.dataBase.getMedicalrecords();
    }

    public void saveDataBaseInFile() throws IOException {
        // TODO : voir si try and catch ou global exeption handler (ou mix des 2 try catch avec exception specifique ? --> mix
        logger.debug("      db - going to save jsonDatabase");
        jsonMapper.writeValue(new File(customProperties.getJsonfilepathname()), this.dataBase);
        logger.debug("      db - save jsonDatabase ok");
    }
}
