package com.esprit.gestionfoyertp.services;

import com.esprit.gestionfoyertp.entities.Universite;

import java.util.List;

public interface UniversiteService {

    public Universite addUniversite(Universite universite);
    public Universite updateUniversite(Universite universite);
    public void deleteUniversiteById(Long idUniversite);
    public Universite getUniversite(Long idUniversite);
    List<Universite> getAllUniversite();
    Universite affecterFoyerAUniversite(Long idFoyer, String nomUniversite);
    Universite desaffecterFoyerAUniversite(long idUniversite);
}
