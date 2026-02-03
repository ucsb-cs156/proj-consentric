package edu.ucsb.cs156.consentric.repositories;

import edu.ucsb.cs156.consentric.entities.Job;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@Repository
@RepositoryRestResource(exported = false)
public interface JobsRepository extends CrudRepository<Job, Long> {}
