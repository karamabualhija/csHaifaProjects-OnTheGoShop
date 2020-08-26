package com.project.OnTheGoShop.Repo;

import com.project.OnTheGoShop.Beans.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findById(int id);
}
