package com.esprit.gestionfoyertp.repositories;

import com.esprit.gestionfoyertp.entities.Chambre;
import com.esprit.gestionfoyertp.entities.Etudiant;
import com.esprit.gestionfoyertp.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findByEtudiantsContainingAndEstValideTrue(Etudiant etudiant);

    @Query("""

            SELECT r FROM Reservation r
    JOIN r.chambre c
    JOIN c.bloc b
    JOIN b.foyer f
    JOIN f.universite u
    WHERE YEAR(r.anneeUniversitaire) = :annee
      AND u.nomUniversite = :nomUniversite
    """)
    List<Reservation> findReservationsByAnneeAndUniversite(
            @Param("annee") int annee,
            @Param("nomUniversite") String nomUniversite);

    List<Reservation> findByChambreAndEstValideTrue(Chambre chambre);
    }