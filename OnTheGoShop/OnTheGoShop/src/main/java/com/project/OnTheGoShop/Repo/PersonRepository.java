package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Person;

import org.springframework.data.repository.CrudRepository;

//@NoRepositoryBean
public interface PersonRepository<T extends Person> extends CrudRepository<T, Integer> {
   // Person findByID(String id);  
    T findById(int Id);
    T findByUsername(String username);


}

