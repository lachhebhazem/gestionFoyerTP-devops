package com.esprit.gestionfoyertp.restcontrollers;

import com.esprit.gestionfoyertp.entities.Etudiant;
import com.esprit.gestionfoyertp.services.EtudiantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Etudiant Management")
@RestController
@AllArgsConstructor
@RequestMapping("/Etudiant")
public class EtudiantRESTController {

    @Autowired
    EtudiantService etudiantService;


    @Operation(summary = "Récupérer un étudiant par id")
    @RequestMapping(value = "getById/{idEtudiant}", method = RequestMethod.GET)
    public Etudiant getEtudiant(@PathVariable("idEtudiant") Long idEtudiant) {
        return etudiantService.getEtudiant(idEtudiant);
    }

    @Operation(summary = "Récupérer tous les étudiants")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Etudiant> getAllEtudiants() {
        return etudiantService.getAllEtudiants();
    }

    @Operation(summary = "Ajouter un étudiant")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Etudiant addEtudiant(@RequestBody Etudiant etudiant) {
        return etudiantService.addEtudiant(etudiant);
    }

    @Operation(summary = "Supprimer un étudiant par id")
    @RequestMapping(value = "/delete/{idEtudiant}", method = RequestMethod.DELETE)
    public void deleteEtudiant(@PathVariable("idEtudiant") Long idEtudiant) {
        etudiantService.deleteEtudiantById(idEtudiant);
    }

    @Operation(summary = "Mettre à jour un étudiant existant")
    @RequestMapping(value = "/update/{idEtudiant}", method = RequestMethod.PUT)
    public Etudiant updateEtudiant(@PathVariable("idEtudiant") Long idEtudiant, @RequestBody Etudiant etudiant) {
        etudiant.setIdEtudiant(idEtudiant);
        return etudiantService.updateEtudiant(etudiant);
    }
}
