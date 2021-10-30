package tn.esprit.spring.service;

import java.text.ParseException;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.services.*;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.services.IDepartementService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartementServiceImplTest {

	private static final Logger l = (Logger) LogManager.getLogger(DepartementServiceImplTest.class);
	
	@Autowired
	IEntrepriseService entrpriseService;
	
	@Autowired
	IDepartementService depService;

	@Test
	public void testRetrieveAllDepartements() {
		List<Departement> listDepartements = depService.getAllDepartements();
		// if there are 7 departements in DB :
		Assert.assertEquals(1, listDepartements.size());
	}
	

	@Test
	public void testRetrieveDep() {
	Departement dep = depService.retrieveDepartement(1);
	Assert.assertEquals(6, dep.getId());
	l.info("retrieveDep : "+ dep);
	}

	@Test
	public void testAddDepartement() throws ParseException {
		Departement dep = new Departement("Securite"); 
		Departement depAdded = depService.addDep(dep); 
		Assert.assertEquals(dep.getName(), depAdded.getName());
		l.info(" Departement ajoutée avec succès");
		}

	@Test
	public void testUpdateDepartement() throws ParseException {
		Departement dep = new Departement(2,"Production"); 
		Departement depAdded = depService.updateDepartement(dep); 
		Assert.assertEquals(dep.getName(), depAdded.getName());
		l.info(" Departement modifiée avec succès");
		}
	
	@Test
	public void testDeleteDep()  {
		depService.deleteDepartement(3);
		Assert.assertNull(depService.retrieveDepartement(3));
		l.info(" Departement supprimée avec succès");
		}

	
	 // 5 tests unitaires
	 
}
