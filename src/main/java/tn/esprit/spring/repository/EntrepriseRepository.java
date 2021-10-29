package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Entreprise;


@Repository
public interface EntrepriseRepository extends CrudRepository<Entreprise, Integer>  {
	
	@Query("SELECT n FROM Entreprise n WHERE n.name=:name")
	public List<Entreprise> findByName(@Param("name") String name);
}

