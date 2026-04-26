package com.xtn.figo.service;

import com.xtn.figo.entities.Consultation;
import com.xtn.figo.entities.Medecin;
import com.xtn.figo.entities.Patient;
import com.xtn.figo.entities.Rendezvous;
import com.xtn.figo.repositories.ConsultationRepository;
import com.xtn.figo.repositories.MedecinRepository;
import com.xtn.figo.repositories.PatientRepository;
import com.xtn.figo.repositories.RendevousRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * @author macbook
 **/
@Service
@Transactional
public class HospitalServiceImpl implements IHospitalService {
    private PatientRepository patientRepository;
    private ConsultationRepository consultationRepository;
    private MedecinRepository medecinRepository;
    private RendevousRepository rendezvousRepository;

    public HospitalServiceImpl(PatientRepository patientRepository, ConsultationRepository consultationRepository, MedecinRepository medecinRepository, RendevousRepository rendezvousRepository) {
        this.patientRepository = patientRepository;
        this.consultationRepository = consultationRepository;
        this.medecinRepository = medecinRepository;
        this.rendezvousRepository = rendezvousRepository;
    }


    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Medecin saveMedecin(Medecin medecin) {
        return medecinRepository.save(medecin);
    }

    @Override
    public Rendezvous saveRDV(Rendezvous rendezvous) {
        return rendezvousRepository.save(rendezvous);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }

    public Patient getPatient(Long id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient findPatientByPrenom(String prenom) {
        return patientRepository.findByPrenom(prenom);
    }

    public Medecin findMedecinById(Long id) {
        return medecinRepository.findById(id).orElse(null);
    }

    public Rendezvous findRDVById(Long id) {
        return rendezvousRepository.findById(id).orElse(null);
    }
}
