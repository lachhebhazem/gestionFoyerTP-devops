package com.esprit.gestionfoyertp.repositories;

import com.esprit.gestionfoyertp.entities.Universite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UniversiteRepository extends JpaRepository<Universite,Long> {
    Optional<Universite> findByNomUniversite(String nomUniversite);
}
