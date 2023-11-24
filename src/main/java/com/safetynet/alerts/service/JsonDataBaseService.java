package com.safetynet.alerts.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.safetynet.alerts.config.CustomProperties;
import com.safetynet.alerts.exception.UnloadedDatabaseException;
import com.safetynet.alerts.exception.UnreachableDatabaseException;
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
    private File jsonFile;

    public JsonDataBaseService(CustomProperties customProperties) {
        this.customProperties = customProperties;
    }

    @Override
    public void run(String[] args) {
        // chargement données depuis fichier JSON au démarrage
        try {
            // TODO voir pourquoi les attributs de firestations et medicalrecords ne fonctionnent pas avec jakson ???
            // --> a priori ok pour load firestation mais reste pb de local date / medical records
            jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

            logger.debug("db - going to Load Database from JSON file : {}", customProperties.getJsonfilepathname());
            this.jsonFile = new File(customProperties.getJsonfilepathname());
            this.dataBase = jsonMapper.readValue(this.jsonFile, JsonDataBase.class);
            logger.info("Loading jsonDatabase complete");

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("unable to Load Database from JSON file {} ", customProperties.getJsonfilepathname());
            throw new UnloadedDatabaseException("Unable to Load Database from JSON file " + customProperties.getJsonfilepathname());
        }

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

    public void saveDataBaseInFile() {
        logger.debug("      db - going to save jsonDatabase");
        try {
            jsonMapper.writeValue(this.jsonFile, this.dataBase);
            logger.debug("      db - save jsonDatabase ok");
        } catch (IOException e) {
            logger.error("unable to save Database into JSON file {} ", this.jsonFile.getPath());
            throw new UnreachableDatabaseException("Unable to store data");
        }
    }
}
