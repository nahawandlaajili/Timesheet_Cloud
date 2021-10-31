package tn.esprit.spring.services;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.repository.DepartementRepository;

@Service
public class DepartementServiceImpl implements IDepartementService {

	@Autowired
	DepartementRepository deptRepository;



	private static final Logger l = LogManager.getLogger(DepartementServiceImpl.class);

	public List<Departement> getAllDepartements() {
		List<Departement> departements = null; 
		try {
		l.info("In retrieveAllDepartement() : ");
		departements = (List<Departement>) deptRepository.findAll();  
		for (Departement dep : departements) {
			l.debug("departement +++ : " + dep);
		} 
		l.info("Out of retrieveAllDepartement() : ");
	}catch (Exception e) {
		l.error("Error in retrieveAllDepartement() : " + e);
	}

	return departements;
	}

	@Override
	public Departement addDep(Departement dep) {
		return deptRepository.save(dep);
	}

	@Override
	public Departement updateDepartement(Departement dep) {
		return deptRepository.save(dep);
	}

	@Override
	public Departement retrieveDepartement(int id) {
		l.info("in  retrieveDepartement id = " + id);
		Departement d =  deptRepository.findById(id).orElse(null);
		//Departement d =   departementRepository.findById(Long.parseLong(id)).orElse(null);
		l.info("departement returned : " + d);
		return d; 
	}

	@Override
	public void deleteDepartement(int id) {
		deptRepository.deleteById(id);
		
	}
	
	@Override
	public Departement getDepartementById(int id) {
		return deptRepository.findById(id).orElse(null);
	}
	
	//done
	@Override
	public void deleteDep(String id) {
		deptRepository.deleteById(Integer.parseInt(id));
	}
	
	
//done
	@Override
	public Departement departementRetrieved(String id) {
		l.info("in  retrieveDep id = " + id);
		Departement dep = deptRepository.findById(Integer.parseInt(id)).orElse(null);
		l.info("dep  retrieveDep id = " + dep);
		return dep;
	}
	
}
