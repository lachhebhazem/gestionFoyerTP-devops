package com.esprit.gestionfoyertp.restcontrollers;

import com.esprit.gestionfoyertp.entities.Universite;
import com.esprit.gestionfoyertp.services.UniversiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Universite Management")
@RestController
@AllArgsConstructor
@RequestMapping("/universite")
public class UniversiteRESTController {

    @Autowired
    UniversiteService universiteService;


    @Operation(summary = "Récupérer une université par id")
    @RequestMapping(value = "/{idUniversite}", method = RequestMethod.GET)
    public Universite getUniversite(@PathVariable("idUniversite") Long idUniversite) {
        return universiteService.getUniversite(idUniversite);
    }

    @Operation(summary = "Récupérer toutes les universités")
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Universite> getAllUniversites() {
        return universiteService.getAllUniversite();
    }

    @Operation(summary = "Ajouter une université")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Universite addUniversite(@RequestBody Universite universite) {
        return universiteService.addUniversite(universite);
    }

    @Operation(summary = "Supprimer une université par id")
    @RequestMapping(value = "/delete/{idUniversite}", method = RequestMethod.DELETE)
    public void deleteUniversite(@PathVariable("idUniversite") Long idUniversite) {
        universiteService.deleteUniversiteById(idUniversite);
    }

    @Operation(summary = "Mettre à jour une université existante")
    @RequestMapping(value = "/update/{idUniversite}", method = RequestMethod.PUT)
    public Universite updateUniversite(@PathVariable("idUniversite") Long idUniversite, @RequestBody Universite universite) {
        universite.setIdUniversite(idUniversite);
        return universiteService.updateUniversite(universite);
    }
    @Operation(summary = "Affecter un foyer à une université")
    @PostMapping("/affecterFoyerAUniversite")
    public ResponseEntity<Universite> affecterFoyerAUniversite(
            @RequestParam("idFoyer") Long idFoyer,
            @RequestParam("nomUniversite") String nomUniversite) {

        if (idFoyer == null || nomUniversite == null || nomUniversite.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Universite universite = universiteService.affecterFoyerAUniversite(idFoyer, nomUniversite);
        if (universite == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(universite);
    }

    @Operation(summary = "Désaffecter le foyer d'une université")
    @DeleteMapping("/desaffecterFoyer/{idUniversite}")
    public ResponseEntity<Universite> desaffecterFoyerAUniversite(
            @PathVariable("idUniversite") long idUniversite) {

        Universite universite = universiteService.desaffecterFoyerAUniversite(idUniversite);

        // Si l'université n'existe pas → 404
        if (universite == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(universite);
    }
}