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
// This interface extends CrudRepository to provide basic CRUD operations for the Entreprise entity.
// It includes a custom query method to find enterprises by their name using JPQL.
// The @Repository annotation indicates that this interface is a Spring Data repository,
// enabling exception translation and other repository-related features.
// The method findByName uses the @Query annotation to define a JPQL query that retrieves
// enterprises based on the provided name parameter. The @Param annotation binds the method parameter to the

// query parameter, allowing for dynamic queries based on the input name.
// The return type is a List of Entreprise, allowing for multiple results to be returned if there are multiple enterprises with the same name.
// This setup allows for easy integration with Spring Data JPA, enabling the application to perform database operations without writing boilerplate code.
// The use of JPQL allows for more complex queries and better abstraction over the underlying database, making the code more maintainable and readable.
// Overall, this repository interface provides a clean and efficient way to interact with the Entreprise entity in the database, leveraging the power of Spring Data JPA for data access operations.