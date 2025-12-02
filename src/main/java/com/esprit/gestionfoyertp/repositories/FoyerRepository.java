package com.esprit.gestionfoyertp.repositories;

import com.esprit.gestionfoyertp.entities.Foyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FoyerRepository extends JpaRepository<Foyer, Long> {

    List<Foyer> findByBlocsNomBlocAndBlocsCapaciteBloc(String nomBloc, long capaciteBloc);

    @Query("SELECT f FROM Foyer f " +
            "INNER JOIN f.blocs b " +
            "INNER JOIN b.chambres c " +
            "GROUP BY f.idFoyer, f.nomFoyer " +
            "ORDER BY COUNT(c) DESC")
    List<Foyer> findFoyerAvecPlusDeChambres();


}
