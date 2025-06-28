package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.spring.entities.Departement;

/**
 * Repository interface for managing Departement entities.
 * Extends JpaRepository to provide CRUD and pagination operations.
 */
@Repository
public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    // Additional custom queries can be declared here if needed

}
// This interface allows for basic CRUD operations on Departement entities
// without the need for boilerplate code. It uses Spring Data JPA's
// JpaRepository, which provides methods like save, findById, findAll, deleteById, etc.
// The @Repository annotation indicates that this interface is a Spring Data repository,
// enabling exception translation and other repository-related features.    