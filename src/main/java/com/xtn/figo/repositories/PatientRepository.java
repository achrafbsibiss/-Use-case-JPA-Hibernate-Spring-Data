package com.xtn.figo.repositories;

import com.xtn.figo.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author macbook
 **/
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByPrenom(String prenom);
}
