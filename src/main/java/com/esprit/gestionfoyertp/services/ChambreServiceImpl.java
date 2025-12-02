package com.esprit.gestionfoyertp.services;


import com.esprit.gestionfoyertp.entities.Chambre;
import com.esprit.gestionfoyertp.entities.Reservation;
import com.esprit.gestionfoyertp.entities.TypeChambre;
import com.esprit.gestionfoyertp.entities.Universite;
import com.esprit.gestionfoyertp.repositories.ChambreRepository;
import com.esprit.gestionfoyertp.repositories.ReservationRepository;
import com.esprit.gestionfoyertp.repositories.UniversiteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChambreServiceImpl implements ChambreService {

    @Autowired
    private ChambreRepository chambreRepository;
    private ReservationRepository reservationRepository;
    private UniversiteRepository universiteRepository;

    @Override
    public Chambre addChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @Override
    public Chambre updateChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @Override
    public void deleteChambreById(Long idChambre) {
        chambreRepository.deleteById(idChambre);
    }

    @Override
    public Chambre getChambreById(Long idChambre) {
        return chambreRepository.findById(idChambre).get();
    }

    @Override
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }


    @Override
    public List<Chambre> getChambresParNomUniversite(String nomUniversite) {

        if (nomUniversite == null || nomUniversite.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'université est obligatoire");
        }

        return chambreRepository.findByBloc_Foyer_Universite_NomUniversite(nomUniversite.trim());
    }


    @Override
    public List<Chambre> getChambresParBlocEtType(long idBloc, TypeChambre typeC) {

        return chambreRepository.findByBlocIdBlocAndTypeC(idBloc, typeC);
    }

//Déclare une méthode qui retourne les chambres NON réservées
//dans une université donnée et d’un type de chambre précis.
    @Override
    public List<Chambre> getChambresNonReserveParNomUniversiteEtTypeChambre(
            String nomUniversite, TypeChambre type) {

        if (nomUniversite == null || nomUniversite.trim().isEmpty() || type == null) {
            return Collections.emptyList();
        }

        // 1. Récupérer toutes les chambres du type voulu dans cette université
        List<Chambre> chambresCandidates = chambreRepository
                .findByBlocFoyerUniversiteNomUniversiteAndTypeC(nomUniversite.trim(), type);

        // 2. Année universitaire actuelle (ex: 2025 → on est en 2025-2026)
        int anneeActuelle = LocalDate.now().getYear();

        // 3. Filtrer celles qui n'ont PAS de réservation valide cette année
        return chambresCandidates.stream()
                .filter(chambre -> {
                    return reservationRepository.findByChambreAndEstValideTrue(chambre).stream()
                            .noneMatch(res -> {
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(res.getAnneeUniversitaire());
                                return cal.get(Calendar.YEAR) == anneeActuelle;
                            });
                })
                .collect(Collectors.toList());
    }
}

