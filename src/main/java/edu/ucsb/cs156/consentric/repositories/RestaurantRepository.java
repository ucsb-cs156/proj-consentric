package edu.ucsb.cs156.consentric.repositories;

import edu.ucsb.cs156.consentric.entities.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/** The RestaurantRepository is a repository for Restaurant entities */
@Repository
@RepositoryRestResource(exported = false)
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {}
