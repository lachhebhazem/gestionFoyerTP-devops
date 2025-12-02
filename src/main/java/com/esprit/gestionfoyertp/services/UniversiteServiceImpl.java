package com.esprit.gestionfoyertp.services;

import com.esprit.gestionfoyertp.entities.Bloc;
import com.esprit.gestionfoyertp.entities.Foyer;
import com.esprit.gestionfoyertp.entities.Universite;
import com.esprit.gestionfoyertp.repositories.FoyerRepository;
import com.esprit.gestionfoyertp.repositories.UniversiteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
;

import java.util.List;

@Service
@AllArgsConstructor
public class UniversiteServiceImpl implements UniversiteService {

    @Autowired
    private UniversiteRepository universiteRepository;
    private FoyerRepository foyerRepository;

    @Override
    public Universite addUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    @Override
    public Universite updateUniversite(Universite universite) {
        return universiteRepository.save(universite);
    }

    @Override
    public void deleteUniversiteById(Long idUniversite) {
        universiteRepository.deleteById(idUniversite);
    }

    @Override
    public Universite getUniversite(Long idUniversite) {
        return universiteRepository.findById(idUniversite).get();
    }

    @Override
    public List<Universite> getAllUniversite() {
        return universiteRepository.findAll();
    }

    @Override
    public Universite affecterFoyerAUniversite(Long idFoyer, String nomUniversite) {

        Foyer foyer = foyerRepository.findById(idFoyer).get();
        Universite universite = universiteRepository.findByNomUniversite(nomUniversite).get();

        universite.setFoyer(foyer);

        return universiteRepository.save(universite);
    }


    @Override

    public Universite desaffecterFoyerAUniversite(long idUniversite) {
        Universite universite = universiteRepository.findById(idUniversite).orElse(null);
        if (universite == null) {
            return null;
        }

        Foyer foyer = universite.getFoyer();
        if (foyer != null) {
            universite.setFoyer(null);
            if (foyer.getUniversite() != null) {
                foyer.setUniversite(null);
                foyerRepository.save(foyer);
            }
        }

        return universiteRepository.save(universite);
    }

}