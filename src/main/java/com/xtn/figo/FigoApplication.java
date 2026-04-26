package com.xtn.figo;

import com.xtn.figo.entities.*;
import com.xtn.figo.repositories.ConsultationRepository;
import com.xtn.figo.repositories.MedecinRepository;
import com.xtn.figo.repositories.RendevousRepository;
import com.xtn.figo.repositories.PatientRepository;
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
    CommandLineRunner start( RendevousRepository rendevousRepository,
                            MedecinRepository medecinRepository, ConsultationRepository consultationRepository,
                            PatientRepository patientRepository) {
        return args -> {
            Stream.of("MT", "Rs", "F1")
                    .forEach(name -> {
                        Patient patient = new Patient();
                        patient.setPrenom(name);
                        patient.setMalade(false);
                        patient.setDateOfBirth(new Date());
                        patientRepository.save(patient);
                    });

            Stream.of("Aymane", "hassan", "hanane")
                    .forEach(name -> {
                        Medecin medecin = new Medecin();
                        medecin.setName(name);
                        medecin.setEmail(name+"@gmail.com");
                        medecin.setSpecialite(Math.random()>0.52?"cardio" : "generaliste");
                        medecinRepository.save(medecin);
                    });


            Patient patient = patientRepository.findById(1L).orElse(null);
            Patient patient2 = patientRepository.findByPrenom("MT");

            Medecin medecin = medecinRepository.findById(1L).orElse(null);


            Rendezvous rendezvous = new Rendezvous();
            rendezvous.setMedecin(medecin);
            rendezvous.setPatient(patient);
            rendezvous.setConsultationDate(new Date());
            rendezvous.setStatus(StatusRDV.PENDING);
            rendevousRepository.save(rendezvous);


           Rendezvous rendezvous2 = rendevousRepository.findById(1L).orElse(null);

           Consultation consultation = new Consultation();
           consultation.setRendezvous(rendezvous2);
           consultation.setRapport("Rapport de consultation");
           consultation.setConsultationDate( rendezvous2.getConsultationDate());
           consultationRepository.save(consultation);


        };
    }
}
