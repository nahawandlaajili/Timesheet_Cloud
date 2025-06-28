package tn.esprit.spring.services;

import java.util.List;
import tn.esprit.spring.entities.Departement;

public interface IDepartementService {	
	public List<Departement> getAllDepartements();
	public Departement addDep(Departement dep);
	public Departement updateDepartement(Departement dep);	
	Departement retrieveDepartement(int id);
	void deleteDepartement(int id);

	public Departement getDepartementById(int id);
	public void deleteDep(String id) ;
	public Departement departementRetrieved(String id);
}
