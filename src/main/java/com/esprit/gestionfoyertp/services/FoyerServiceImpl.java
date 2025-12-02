package com.esprit.gestionfoyertp.services;


import com.esprit.gestionfoyertp.entities.Foyer;
import com.esprit.gestionfoyertp.entities.Bloc;
import com.esprit.gestionfoyertp.repositories.FoyerRepository;
import com.esprit.gestionfoyertp.repositories.UniversiteRepository;
import com.esprit.gestionfoyertp.entities.Universite;
import com.esprit.gestionfoyertp.repositories.BlocRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FoyerServiceImpl implements FoyerService {

    @Autowired
    private FoyerRepository foyerRepository;
    private UniversiteRepository universiteRepository;
    private BlocRepository blocRepository;

    @Override
    public Foyer addFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }

    @Override
    public Foyer updateFoyer(Foyer foyer) {
        return foyerRepository.save(foyer);
    }


    @Override
    public void deleteFoyerById(Long idFoyer) {
        foyerRepository.deleteById(idFoyer);
    }

    @Override
    public Foyer getFoyer(Long idFoyer) {
        return foyerRepository.findById(idFoyer).orElse(null);
    }

    @Override
    public List<Foyer> getAllFoyers() {
        return foyerRepository.findAll();
    }

    @Override
    @Transactional
    public Foyer ajouterFoyerEtAffecterAUniversite(Foyer foyer, long idUniversite) {

        Universite universite = universiteRepository.findById(idUniversite)
                .orElseThrow(() -> new RuntimeException("Université non trouvée avec id : " + idUniversite));

        List<Bloc> blocs = foyer.getBlocs();

        foyer.setBlocs(null);

        Foyer foyerSaved = foyerRepository.save(foyer);

        if (blocs != null && !blocs.isEmpty()) {
            for (Bloc bloc : blocs) {
                bloc.setIdBloc(0);          // forcer création d'un nouveau bloc
                bloc.setFoyer(foyerSaved);  // relation
            }
            List<Bloc> savedBlocs = blocRepository.saveAll(blocs);
            foyerSaved.setBlocs(savedBlocs);
        }

        universite.setFoyer(foyerSaved);
        universiteRepository.save(universite);

        foyerSaved.setUniversite(universite);
        return foyerRepository.save(foyerSaved);
    }

}
