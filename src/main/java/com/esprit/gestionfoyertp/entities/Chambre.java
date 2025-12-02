package com.esprit.gestionfoyertp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Data
public class Chambre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idChambre;
    private long numChambre;
    @Enumerated(EnumType.STRING)
    private TypeChambre typeC;


    @ManyToOne
    @JoinColumn(name = "bloc_id")
    @JsonIgnoreProperties("chambres")
    private Bloc bloc;

    // RELATION CORRECTE : mappedBy = "chambre" (le nom du champ dans Reservation)
    @OneToMany(mappedBy = "chambre", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("chambre")  // Évite la boucle infinie
    private List<Reservation> reservations = new ArrayList<>();
}



