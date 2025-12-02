package com.esprit.gestionfoyertp.restcontrollers;


import com.esprit.gestionfoyertp.entities.Chambre;
import com.esprit.gestionfoyertp.entities.TypeChambre;
import com.esprit.gestionfoyertp.services.ChambreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Chambre Management")
@RestController
@RequestMapping("/chambre")
public class ChambreRESTController {

    @Autowired
    private ChambreService chambreService;

    @Operation(summary = "Récupérer toutes les chambres")
    @RequestMapping(value = "/getAll",method = RequestMethod.GET)
    public List<Chambre> getAllChambres() {
        return chambreService.getAllChambres();
    }

    @Operation(summary = "Récupérer une chambre par id")
    @RequestMapping(value = "/getById/{idChambre}",method = RequestMethod.GET)
    public Chambre getChambreById(@PathVariable("idChambre") Long idChambre) {
        return chambreService.getChambreById(idChambre);
    }

    @Operation(summary = "Ajouter une chambre")
    @RequestMapping(value = "/add" , method = RequestMethod.POST)
    public Chambre addChambre (@RequestBody Chambre chambre){
        return chambreService.addChambre(chambre);
    }

    @Operation(summary = "Supprimer une chambre par id")
    @RequestMapping(value = "/delete/{idChambre}" , method = RequestMethod.DELETE)
    public void deleteChambreById(@PathVariable("idChambre") Long idChambre){
        chambreService.deleteChambreById(idChambre);
    }

    @Operation(summary = "Mettre à jour une chambre existante")
    @RequestMapping(value = "/update/{idChambre}" , method = RequestMethod.PUT)
    public Chambre updateChambre(@PathVariable("idChambre") Long idChambre, @RequestBody Chambre chambre){
        chambre.setIdChambre(idChambre);
        return chambreService.updateChambre(chambre);
    }

    @GetMapping("/chambres/universite/{nomUniversite:.+}")
    @Operation(summary = "Récupérer toutes les chambres d'une université par son nom")
    public ResponseEntity<List<Chambre>> getChambresParNomUniversite(
            @PathVariable String nomUniversite) {

        List<Chambre> chambres = chambreService.getChambresParNomUniversite(nomUniversite);
        return ResponseEntity.ok(chambres);
    }

    @GetMapping("/bloc/{idBloc}/type/{typeC}")
    @Operation(summary = "Récupérer les chambres d'un bloc par type (SIMPLE/DOUBLE/TRIPLE)")
    public ResponseEntity<List<Chambre>> getChambresParBlocEtType(
            @PathVariable long idBloc,
            @PathVariable TypeChambre typeC) {

        List<Chambre> chambres = chambreService.getChambresParBlocEtType(idBloc, typeC);
        return ResponseEntity.ok(chambres);
    }

    @GetMapping("/chambres/non-reservees/{nomUniversite}/{type}")
    public ResponseEntity<List<Chambre>> getChambresNonReservees(
            @PathVariable String nomUniversite,
            @PathVariable TypeChambre type) {

        List<Chambre> chambresLibres = chambreService
                .getChambresNonReserveParNomUniversiteEtTypeChambre(nomUniversite, type);

        return ResponseEntity.ok(chambresLibres);
    }


}
