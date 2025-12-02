package com.esprit.gestionfoyertp.restcontrollers;

import com.esprit.gestionfoyertp.entities.Reservation;
import com.esprit.gestionfoyertp.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Tag(name = "Reservation Management")
@RestController
@AllArgsConstructor
@RequestMapping("/reservation")
public class ReservationRESTController {

    @Autowired
    ReservationService reservationService;

    @Operation(summary = "Récupérer une réservation par id")
    @RequestMapping(value = "/getByid/{idReservation}", method = RequestMethod.GET)
    public Reservation getReservation(@PathVariable("idReservation") Long idReservation) {
        return reservationService.getReservation(idReservation);
    }

    @Operation(summary = "Récupérer toutes les réservations")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @Operation(summary = "Ajouter une réservation")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Reservation addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    @Operation(summary = "Supprimer une réservation par id")
    @RequestMapping(value = "delete/{idReservation}", method = RequestMethod.DELETE)
    public void deleteReservation(@PathVariable("idReservation") Long idReservation) {
        reservationService.deleteReservation(idReservation);
    }

    @Operation(summary = "Mettre à jour une réservation existante")
    @RequestMapping(value = "update/{idReservation}", method = RequestMethod.PUT)
    public Reservation updateReservation(@PathVariable("idReservation") Long idReservation, @RequestBody Reservation reservation) {
        reservation.setIdReservation(idReservation);
        return reservationService.updateReservation(reservation);
    }


    @PostMapping("/AjouterReservation")
    @Operation(summary = "Ajouter une réservation pour un étudiant dans une chambre disponible du bloc")
    public ResponseEntity<Reservation> ajouterReservation(
            @RequestParam("idBloc") int idBloc,
            @RequestParam("cinEtudiant") long cinEtudiant) {

        Reservation reservation = reservationService.ajouterReservation(idBloc, cinEtudiant);
        return ResponseEntity.ok(reservation);

    }

    @Operation(summary = "Annuler une reservation par cinEtudiant")
    @RequestMapping(value = "/annuler/{cinEtudiant}", method = RequestMethod.PUT)
    public Reservation annulerReservation(@PathVariable long cinEtudiant) {
        return reservationService.annulerReservation(cinEtudiant);
    }

    @GetMapping("/reservations/universite/{nomUniversite}/annee/{annee}")
    public ResponseEntity<List<Reservation>> getReservations(
            @PathVariable String nomUniversite,
            @PathVariable("annee") @DateTimeFormat(pattern = "yyyy") Date anneeUniversite) {

        var reservations = reservationService
                .getReservationParAnneeUniversitaireEtNomUniversite(anneeUniversite, nomUniversite);

        return ResponseEntity.ok(reservations);
    }

}