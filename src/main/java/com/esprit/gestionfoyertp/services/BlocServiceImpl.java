package com.esprit.gestionfoyertp.services;


import com.esprit.gestionfoyertp.entities.Bloc;
import com.esprit.gestionfoyertp.entities.Chambre;
import com.esprit.gestionfoyertp.repositories.BlocRepository;
import com.esprit.gestionfoyertp.repositories.ChambreRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BlocServiceImpl implements BlocService {

    @Autowired
    private BlocRepository blocRepository;
    private ChambreRepository chambreRepository;

    private FoyerService foyerService;

    @Override
    public Bloc addBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public Bloc updateBloc(Bloc bloc) {
        return blocRepository.save(bloc);
    }

    @Override
    public void deleteBlocById(int idBloc) {
        blocRepository.deleteById(idBloc);
    }

    @Override
    public Bloc getBlocById(int id) {
        return blocRepository.findById(id).get();
    }

    @Override
    public List<Bloc> getAllBlocs() {
        return blocRepository.findAll();
    }


    @Override
    public Bloc affecterChambresABloc(List<Long> numChambre, long idBloc) {
        // convertir long -> int en vérifiant les limites
        final int id;
        try {
            id = Math.toIntExact(idBloc);
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException("idBloc hors limites: " + idBloc, ex);
        }

        Bloc bloc = blocRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloc non trouvé avec l'id : " + idBloc));

        if (numChambre == null || numChambre.isEmpty()) {
            return bloc;
        }

        for (Long numero : numChambre) {
            if (numero == null) continue;
            Chambre chambre = chambreRepository.findByNumChambre(numero)
                    .orElseThrow(() -> new RuntimeException("Chambre non trouvée avec le numéro : " + numero));
            chambre.setBloc(bloc);
            chambreRepository.save(chambre); // persister l'affectation
        }

        return blocRepository.save(bloc);
    }


}
