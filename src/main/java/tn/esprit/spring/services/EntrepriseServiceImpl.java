package tn.esprit.spring.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	private static final Logger l = LogManager.getLogger(EntrepriseServiceImpl.class);

	@Autowired
	EntrepriseRepository entrepriseRepository;
	@Autowired
	DepartementRepository deptRepoistory;

	@Override
	public List<Entreprise> getAllEntreprises() {
		return (List<Entreprise>) entrepriseRepository.findAll();
	}

	@Override
	public Entreprise addEntreprise(Entreprise en) {
		return entrepriseRepository.save(en);
	}
	@Override
	public Entreprise updateEntreprise(Entreprise en) {
		return entrepriseRepository.save(en);
	}

	
	@Override
	public Entreprise RetrieveEntreprise(int id) {
		l.info("in  retrieveEntreprise id = " + id);
		Entreprise en =  entrepriseRepository.findById(id).orElse(null); 
		l.info("Entreprise returned : " + en);
		return en; 
	}
	
	@Override
	public void deleteEnt(int id) {
		entrepriseRepository.deleteById(id);
		
	}

}