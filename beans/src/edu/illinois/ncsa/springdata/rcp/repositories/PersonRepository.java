package edu.illinois.ncsa.springdata.rcp.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.illinois.ncsa.springdata.rcp.domain.Person;

public interface PersonRepository extends CrudRepository<Person, String> {
}
