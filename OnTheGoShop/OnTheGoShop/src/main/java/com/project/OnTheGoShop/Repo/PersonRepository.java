package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, Integer> {
    Person findByID(String id);
}
