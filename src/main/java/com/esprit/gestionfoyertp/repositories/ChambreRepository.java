package com.esprit.gestionfoyertp.repositories;

import com.esprit.gestionfoyertp.entities.Chambre;
import com.esprit.gestionfoyertp.entities.Foyer;
import com.esprit.gestionfoyertp.entities.TypeChambre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre,Long> {
    Optional<Chambre> findByNumChambre(Long numChambre);
    List<Chambre> findByBloc_Foyer_Universite_NomUniversite(String nomUniversite);
    List<Chambre> findByBlocIdBlocAndTypeC(Long idBloc, TypeChambre typeC);

    List<Chambre> findByBlocFoyerUniversiteNomUniversiteAndTypeC(String nomUniversite, TypeChambre typeC);

    @Query("SELECT c.typeC, COUNT(c) FROM Chambre c " +
            "INNER JOIN c.bloc b " +
            "INNER JOIN b.foyer f " +
            "INNER JOIN f.universite u " +
            "WHERE u.nomUniversite = :idU " +
            "GROUP BY c.typeC")
    List<Object[]> countChambresByTypeAndUniversite(@Param("idU") String idUniversite);


    @Query("SELECT f FROM Foyer f " +
            "JOIN f.blocs b " +
            "JOIN b.chambres c " +
            "GROUP BY f " +
            "ORDER BY COUNT(c) DESC")
    List<Foyer> findFoyerAvecLePlusDeChambres();
}
