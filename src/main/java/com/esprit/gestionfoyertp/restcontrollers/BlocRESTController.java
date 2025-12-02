package com.esprit.gestionfoyertp.restcontrollers;

import com.esprit.gestionfoyertp.entities.Bloc;
import com.esprit.gestionfoyertp.services.BlocService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Bloc")
@RestController
@RequestMapping("/bloc")
public class BlocRESTController {

    @Autowired
    BlocService blocService;

    @Operation(summary = "Récupérer tous les blocs")
    @GetMapping("/getAll")
    public List<Bloc> getAll() {
        return blocService.getAllBlocs();
    }

    @Operation(summary = "Récupérer un bloc par id")
    @GetMapping("/getById/{idBloc}")
    public Bloc getBlocById(@PathVariable int idBloc) {
        return blocService.getBlocById(idBloc);
    }

   @RequestMapping(value = "/add", method = RequestMethod.POST)
    @Operation(summary = "Ajouter un nouveau bloc")
    public Bloc addBloc(@RequestBody Bloc bloc) {
        // ensure a new entity is created (avoid accidental update)
        bloc.setIdBloc(0);
        return blocService.addBloc(bloc);
   }

    @Operation(summary = "Supprimer un bloc par id")
    @DeleteMapping("/delete/{idBloc}")
    public void deleteBloc(@PathVariable int idBloc) {
        blocService.deleteBlocById(idBloc);
    }

    @Operation(summary = "Mettre à jour un bloc")
    @PutMapping("/updateBloc/{idBloc}")
    public Bloc updateBloc(@PathVariable int idBloc, @RequestBody Bloc bloc) {
        bloc.setIdBloc(idBloc);
        return blocService.updateBloc(bloc);
    }
    //********************Affectation des chambre a un bloc**********************//

    @Operation(summary = "Affecter plusieurs chambres à un bloc par leurs numéros")
    @PostMapping("/affecterChambres")
    public ResponseEntity<Bloc> affecterChambresABloc(
            @RequestParam("numChambre") List<Long> numChambre,
            @RequestParam("idBloc") long idBloc) {

        Bloc blocMisAJour = blocService.affecterChambresABloc(numChambre, idBloc);
        return ResponseEntity.ok(blocMisAJour);
    }
}