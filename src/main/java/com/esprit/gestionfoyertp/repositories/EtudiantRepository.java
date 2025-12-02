package com.esprit.gestionfoyertp.repositories;

import com.esprit.gestionfoyertp.entities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant,Long> {

    Etudiant findEtudiantByCin(Long cin);


    @Query("SELECT e FROM Etudiant e " +
            "JOIN e.reservations r " +
            "WHERE r.estValide = true " +
            "AND FUNCTION('YEAR', r.anneeUniversitaire) = :annee")
    List<Etudiant> findEtudiantsAvecReservationValide(@Param("annee") int annee);

    @Query("SELECT e FROM Etudiant e " +
            "WHERE NOT EXISTS (" +
            "   SELECT 1 FROM e.reservations r" +
            ")")
    List<Etudiant> findEtudiantsSansAucuneReservation();
}
