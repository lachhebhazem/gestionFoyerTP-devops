package com.esprit.gestionfoyertp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReservation;
    private Date anneeUniversitaire;
    private String numReservation;
    private boolean estValide;


    @ManyToMany
    @JsonManagedReference
    private List<Etudiant> etudiants;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "id_chambre")
    private Chambre chambre;


}
