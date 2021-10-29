package tn.esprit.spring.service;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.services.IEntrepriseService;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {

	private static final Logger l = (Logger) LogManager.getLogger(EntrepriseServiceImplTest.class);
	
	@Autowired
	IEntrepriseService entrepriseService;

	@Test
	public void testRetrieveAllEntreprises() {
		List<Entreprise> listEntreprises = entrepriseService.getAllEntreprises(); 
		// if there are 7 users in DB : 
		Assert.assertEquals(2, listEntreprises.size());
	}


	@Test
	public void B_testRetrieveEntreprise() {
		Entreprise entrepriseRetrieved = entrepriseService.RetrieveEntreprise(6); 
		Assert.assertEquals(2, entrepriseRetrieved.getId());
		l.info("retrieve Entreprise : "+ entrepriseRetrieved);
	}

	@Test
	public void testAddEntreprise() throws ParseException {
		Entreprise en = new Entreprise ("Sodefa","PME"); 
		Entreprise dentrepriseAdded = entrepriseService.addEntreprise(en); 
		Assert.assertEquals(en.getName(), dentrepriseAdded.getName());
		l.info(" Entreprise ajoutée avec succès");
	}
	
	@Test
	public void testUpdateEntreprise() throws ParseException {
		Entreprise en = new Entreprise (2,"Renaut","PME"); 
		Entreprise entrepriseUpdated = entrepriseService.updateEntreprise(en); 
		Assert.assertEquals(en.getName(), entrepriseUpdated.getName());
		l.info(" Entreprise modifiée avec succès");
	}

	@Test
	public void testDeleteEnt()  {
		entrepriseService.deleteEnt(1);
		Assert.assertNull(entrepriseService.RetrieveEntreprise(1));
		l.info(" Entreprise supprimée avec succès");
	}

}
