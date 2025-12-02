package com.esprit.gestionfoyertp.services;


import com.esprit.gestionfoyertp.entities.Bloc;

import java.util.List;

public interface BlocService {

    public Bloc addBloc(Bloc bloc);
    public Bloc updateBloc(Bloc bloc);
    public void deleteBlocById(int idBloc);
    Bloc getBlocById(int idBloc);
    List<Bloc> getAllBlocs();
    Bloc affecterChambresABloc(List<Long> numChambre, long idBloc);




}
