package com.esprit.gestionfoyertp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Data
public class Foyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idFoyer;
    private String nomFoyer;
    private long capaciteFoyer;

    @OneToOne(mappedBy = "foyer")

    @JsonIgnoreProperties("foyer")
    private Universite universite;

    @OneToMany(mappedBy = "foyer" , cascade = CascadeType.ALL)

    @JsonIgnoreProperties("foyer")
    private List<Bloc> blocs;

}
