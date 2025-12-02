package com.esprit.gestionfoyertp.services;

import com.esprit.gestionfoyertp.entities.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationService {

    public Reservation addReservation(Reservation reservation);
    public Reservation updateReservation(Reservation reservation);
    public void deleteReservation(Long idReservation);
    public Reservation getReservation(Long idReservation);
    List<Reservation> getAllReservations();
    public Reservation ajouterReservation(int idBloc, long cinEtudiant);
    public Reservation annulerReservation (long cinEtudiant);
    public List<Reservation> getReservationParAnneeUniversitaireEtNomUniversite(
            Date anneeUniversite, String nomUniversite);
}
