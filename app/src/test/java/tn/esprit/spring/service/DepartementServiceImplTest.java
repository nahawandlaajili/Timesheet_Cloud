package tn.esprit.spring.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.services.IDepartementService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DepartementServiceImplTest {

    private static final Logger l = LogManager.getLogger(DepartementServiceImplTest.class);

    @Autowired
    private IDepartementService depService;

    @Test
    public void testAddDepartement() {
        Departement dep = new Departement();
        dep.setName("Informatique");

        Departement depAdded = depService.addDep(dep);
        assertNotNull(depAdded);
        assertEquals(dep.getName(), depAdded.getName());

        l.info("Departement ajoutée avec succès : " + depAdded);
    }

    @Test
    public void testRetrieveAllDepartements() {
        List<Departement> listDepartements = depService.getAllDepartements();
        assertNotNull(listDepartements);
        assertTrue(listDepartements.size() >= 0);

        l.info("Nombre total de departements : " + listDepartements.size());
    }

    @Test
    public void testRetrieveDep() {
        int testId = 6; // Ensure this ID exists in your DB or mock it
        Departement dep = depService.retrieveDepartement(testId);
        assertNotNull(dep);
        assertEquals(testId, dep.getId());

        l.info("retrieveDep : " + dep);
    }

    @Test
    public void testUpdateDepartement() {
        Departement dep = new Departement();
        dep.setName("Mathématiques");

        Departement created = depService.addDep(dep);

        created.setName("Physique");
        Departement updated = depService.updateDepartement(created);

        assertEquals("Physique", updated.getName());

        l.info("Departement modifiée avec succès : " + updated);
    }

    @Test
    public void testDeleteDepart() {
        Departement dep = new Departement();
        dep.setName("TestSuppression");

        Departement depNew = depService.addDep(dep);
        int id = depNew.getId();

        depService.deleteDepartement(id);
        Departement deleted = depService.getDepartementById(id); // Assumes method returns null if not found

        assertNull(deleted);
        l.info("Dep supprimé : " + depNew);
    }
}
