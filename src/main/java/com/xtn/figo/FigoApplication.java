package com.xtn.figo;

import com.xtn.figo.entities.*;
import com.xtn.figo.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.stream.Stream;

@SpringBootApplication
public class FigoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FigoApplication.class, args);
    }

    @Bean
    CommandLineRunner start(IHospitalService hospitalService) {
        return args -> {

            // Patients
            Stream.of("MT", "Rs", "F1").forEach(name -> {
                Patient patient = new Patient();
                patient.setPrenom(name);
                patient.setMalade(false);
                patient.setDateOfBirth(new Date());
                hospitalService.savePatient(patient);
            });

            // Medecins
            Stream.of("Aymane", "Hassan", "Hanane").forEach(name -> {
                Medecin medecin = new Medecin();
                medecin.setName(name);
                medecin.setEmail(name + "@gmail.com");
                medecin.setSpecialite(Math.random() > 0.5 ? "Cardio" : "Generaliste");
                hospitalService.saveMedecin(medecin);
            });

            // Get data
            Patient patient = hospitalService.getPatient(1L);
            Medecin medecin = hospitalService.findMedecinById(1L);

            // RDV
            Rendezvous rdv = new Rendezvous();
            rdv.setPatient(patient);
            rdv.setMedecin(medecin);
            rdv.setConsultationDate(new Date());
            rdv.setStatus(StatusRDV.PENDING);
            hospitalService.saveRDV(rdv);

            // Consultation
            Rendezvous savedRDV = hospitalService.findRDVById(1L);

            Consultation consultation = new Consultation();
            consultation.setRendezvous(savedRDV);
            consultation.setRapport("Rapport de consultation");
            consultation.setConsultationDate(savedRDV.getConsultationDate());

            hospitalService.saveConsultation(consultation);
        };
    }
}