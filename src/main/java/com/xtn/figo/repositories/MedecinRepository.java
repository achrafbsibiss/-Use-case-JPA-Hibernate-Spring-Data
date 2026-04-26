package com.xtn.figo.repositories;

import com.xtn.figo.entities.Medecin;
import com.xtn.figo.entities.Rendezvous;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author macbook
 **/
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Medecin findByName(String name);
}
