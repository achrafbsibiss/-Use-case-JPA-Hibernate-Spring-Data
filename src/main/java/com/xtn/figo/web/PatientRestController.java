package com.xtn.figo.web;

import com.xtn.figo.entities.Patient;
import com.xtn.figo.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author macbook
 **/
@RestController
public class PatientRestController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/patients")
    public List<Patient> patientList() {
        return patientRepository.findAll();
    }
}
