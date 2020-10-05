package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

import javax.transaction.Transactional;

@Transactional
public interface UserRepository extends PersonRepository<User> ,CrudRepository<User, Integer> {
    User findById(int id);
    User findByUsername(String username);
    ArrayList<User> findAll();

   
}
