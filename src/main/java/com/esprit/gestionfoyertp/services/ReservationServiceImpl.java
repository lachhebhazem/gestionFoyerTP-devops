package com.esprit.gestionfoyertp.services;


import com.esprit.gestionfoyertp.entities.Bloc;
import com.esprit.gestionfoyertp.entities.Chambre;
import com.esprit.gestionfoyertp.entities.Etudiant;
import com.esprit.gestionfoyertp.entities.Reservation;
import com.esprit.gestionfoyertp.repositories.BlocRepository;
import com.esprit.gestionfoyertp.repositories.ChambreRepository;
import com.esprit.gestionfoyertp.repositories.EtudiantRepository;
import com.esprit.gestionfoyertp.repositories.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    private BlocRepository blocRepository;
    private EtudiantRepository etudiantRepository;
    private ChambreRepository chambreRepository;

    @Override
    public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void deleteReservation(Long idReservation) {
        reservationRepository.deleteById(idReservation);
    }


    @Override
    public Reservation getReservation(Long idReservation) {
        return reservationRepository.findById(idReservation).get();
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }



    @Override
    public Reservation ajouterReservation(int idBloc, long cinEtudiant) {


        Etudiant etudiant = etudiantRepository.findEtudiantByCin(cinEtudiant);
        if (etudiant == null) return null;

        List<Chambre> chambres = chambreRepository.findAll()
                .stream()
                .filter(c -> c.getBloc().getIdBloc() == idBloc)
                .toList();

        Chambre chambreDisponible = null;
        for (Chambre c : chambres) {
            int maxCapacity = switch (c.getTypeC()) {
                case SIMPLE -> 1;
                case DOUBLE -> 2;
                case TRIPLE -> 3;
            };
            if (c.getReservations() == null || c.getReservations().size() < maxCapacity) {
                chambreDisponible = c;
                break;
            }
        }

        if (chambreDisponible == null) {
            return null;
        }

        Reservation reservation = new Reservation();
        reservation.setAnneeUniversitaire(new java.util.Date());
        reservation.setEstValide(true);

        reservation.setEtudiants(List.of(etudiant));

        if (chambreDisponible.getReservations() == null) {
            chambreDisponible.setReservations(new java.util.ArrayList<>());
        }
        chambreDisponible.getReservations().add(reservation);
        reservation.setChambre(chambreDisponible);
//pour generer un numReservation unique
        String annee = String.valueOf(java.time.Year.now().getValue());
        String numReservation = chambreDisponible.getNumChambre() + "-"
                + chambreDisponible.getBloc().getNomBloc() + "-"
                + annee;
        reservation.setNumReservation(numReservation);

        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation annulerReservation(long cinEtudiant) {
        Etudiant etudiant = etudiantRepository.findEtudiantByCin(cinEtudiant);
        List<Reservation> reservationsActives = reservationRepository.findByEtudiantsContainingAndEstValideTrue(etudiant);

        if (reservationsActives.isEmpty()) {
            throw new RuntimeException("Aucune réservation active trouvée pour l'étudiant avec CIN: " + cinEtudiant);
        }

        Reservation reservation = reservationsActives.get(0);
        reservation.setEstValide(false);
        if (reservation.getEtudiants() != null) {
            boolean removed = reservation.getEtudiants().removeIf(etud -> etud.getCin() == cinEtudiant);
            System.out.println("Étudiant désaffecté: " + (removed ? "OUI" : "NON"));
        }
        if (reservation.getChambre() != null) {
            Chambre chambre = reservation.getChambre();
            if (chambre.getReservations() != null) {
                chambre.getReservations().removeIf(res -> res.getIdReservation() == reservation.getIdReservation());
            }
            chambreRepository.save(chambre);
            System.out.println("Chambre désaffectée - Capacité mise à jour");
            reservation.setChambre(null);
        }
        Reservation reservationAnnulee = reservationRepository.save(reservation);

        System.out.println("✅ Réservation annulée avec succès - ID: " + reservationAnnulee.getIdReservation());
        return reservationAnnulee;
    }

    @Override
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(
            Date anneeUniversite, String nomUniversite) {

        if (anneeUniversite == null || nomUniversite == null || nomUniversite.isBlank()) {
            throw new IllegalArgumentException("Paramètres manquants");
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(anneeUniversite);
        int annee = cal.get(Calendar.YEAR);

        return reservationRepository.findReservationsByAnneeAndUniversite(annee, nomUniversite.trim());
    }



}


