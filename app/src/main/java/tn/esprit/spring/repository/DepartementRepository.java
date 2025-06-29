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

