package com.esprit.gestionfoyertp.services;

import com.esprit.gestionfoyertp.entities.Etudiant;

import java.util.List;

public interface EtudiantService {

    public Etudiant addEtudiant(Etudiant etudiant );
    public Etudiant updateEtudiant(Etudiant etudiant);
    public void deleteEtudiantById(Long idEtudiant);
    public Etudiant getEtudiant(Long idEtudiant);
    List<Etudiant> getAllEtudiants();
}
