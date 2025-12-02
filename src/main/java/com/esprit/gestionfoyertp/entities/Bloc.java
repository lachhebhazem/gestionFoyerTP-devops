package com.esprit.gestionfoyertp.entities;

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
public class Bloc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idBloc;
	private String nomBloc;
    private Long capaciteBloc;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "foyer_id")
    @JsonIgnoreProperties({"blocs", "universite"})
    private Foyer foyer;

    @OneToMany(mappedBy = "bloc")
    @JsonIgnoreProperties({"bloc", "reservations"})
    private List<Chambre> chambres;

}
