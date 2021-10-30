package tn.esprit.spring.services;


import java.util.List;

import tn.esprit.spring.entities.Entreprise;


public interface IEntrepriseService {
	public List<Entreprise> getAllEntreprises();
	public Entreprise addEntreprise (Entreprise en);
	public Entreprise updateEntreprise(Entreprise en) ;
	public Entreprise RetrieveEntreprise(int id);
    public void deleteEnt(int id);
	
    public Entreprise retrieveEnt(String id);
}