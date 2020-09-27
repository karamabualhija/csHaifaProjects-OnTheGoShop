package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.Person;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonRepository<T extends Person> extends CrudRepository<T, Integer> {
   // Person findByID(String id);  
    T findByID(String id);
    T findByUsername(String username);


}
