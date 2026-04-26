package com.xtn.figo.repositories;

import com.xtn.figo.entities.Consultation;
import com.xtn.figo.entities.Rendezvous;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author macbook
 **/
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
}
