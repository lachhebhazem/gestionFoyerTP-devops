package com.esprit.gestionfoyertp.services;

import com.esprit.gestionfoyertp.entities.Foyer;

import java.util.List;

public interface FoyerService {

    public Foyer addFoyer(Foyer foyer);
    public Foyer updateFoyer(Foyer foyer);
    public void deleteFoyerById(Long idFoyer);
    public Foyer getFoyer(Long idFoyer);
    List<Foyer> getAllFoyers();
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite);

}
