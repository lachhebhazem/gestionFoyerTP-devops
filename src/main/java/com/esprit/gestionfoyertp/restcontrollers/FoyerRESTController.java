package com.esprit.gestionfoyertp.restcontrollers;

import com.esprit.gestionfoyertp.entities.Foyer;
import com.esprit.gestionfoyertp.services.FoyerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Foyer Management")
@RestController
@AllArgsConstructor
@RequestMapping("/foyer")
public class FoyerRESTController {

    @Autowired
    private FoyerService foyerService;

    @Operation(summary = "Récupérer un foyer par id")
    @GetMapping("/getfoyer/{idFoyer}")
    public Foyer getFoyer(@PathVariable Long idFoyer) {
        return foyerService.getFoyer(idFoyer);
    }

    @Operation(summary = "Récupérer tous les foyers")
    @GetMapping("/getAll")
    public List<Foyer> getAllFoyers() {
        return foyerService.getAllFoyers();
    }

    @Operation(summary = "Ajouter un foyer")
    @PostMapping("/add")
    public Foyer addFoyer(@RequestBody Foyer foyer) {
        return foyerService.addFoyer(foyer);
    }

    @Operation(summary = "Supprimer un foyer par id")
    @DeleteMapping("/delete/{idFoyer}")
    public void deleteFoyer(@PathVariable Long idFoyer) {
        foyerService.deleteFoyerById(idFoyer);
    }

    @Operation(summary = "Mettre à jour un foyer existant")
    @PutMapping("/update/{idFoyer}")
    public Foyer updateFoyer(@PathVariable Long idFoyer, @RequestBody Foyer foyer) {
        foyer.setIdFoyer(idFoyer);
        return foyerService.updateFoyer(foyer);
    }

    @Operation(summary = "Ajouter un foyer + blocs + affecter université")
    @PostMapping("/ajouterEtAffecter/{idUniversite}")
    public ResponseEntity<Foyer> ajouterFoyerEtAffecterAUniversite(
            @PathVariable long idUniversite,
            @RequestBody Foyer foyer) {

        Foyer foyerCree = foyerService.ajouterFoyerEtAffecterAUniversite(foyer, idUniversite);

        return ResponseEntity
                .created(URI.create("/foyer/getfoyer/" + foyerCree.getIdFoyer()))
                .body(foyerCree);
    }
}
