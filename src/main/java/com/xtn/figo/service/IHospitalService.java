package com.xtn.figo.service;

import com.xtn.figo.entities.*;

public interface IHospitalService {
    Patient savePatient(Patient patient);
    Medecin saveMedecin(Medecin medecin);
    Rendezvous saveRDV(Rendezvous rendezvous);
    Consultation saveConsultation(Consultation consultation);

    Patient getPatient(Long id);
    Patient findPatientByPrenom(String prenom);
    Medecin findMedecinById(Long id);
    Rendezvous findRDVById(Long id);
}